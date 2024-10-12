(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.handler :refer [notify]]
            [clojure.java.io :as io]))

(defn file-exists?
  [context path]
  (let [opts (assoc context :throw-errors false)]
    (= 0 (:code (command "test" ["-f" path] opts)))))

(defn dir-exists?
  [context path]
  (let [opts (assoc context :throw-errors false)]
    (= 0 (:code (command "test" ["-d" path] opts)))))

(defn link-exists?
  [context path]
  (let [opts (assoc context :throw-errors false)]
    (= 0 (:code (command "test" ["-L" path] opts)))))

(defn file
  [context path {:keys [owner state src sudo]}]
  (let [desired-state (or state :file)
        is-dir (dir-exists? context path)
        is-file (file-exists? context path)
        is-link (link-exists? context path)]
    (when (and (= desired-state :absent) (or is-file is-dir is-link))
      (command "rm" ["-rf" path] context))
    (when (and (= desired-state :file) (not is-file) (not is-dir))
      (command "touch" [path] context (when sudo :sudo)))
    (when (and (= desired-state :dir) (not is-dir))
      (println (str "Creating directory: " path))
      (command "mkdir" ["-p" path] context :sudo)
      (command "chown" [(str (:username context) ":") path] context :sudo)
      (notify context "created"))
    (when (= desired-state :link)
      (println (str "Linking " src " to " path))
      (if is-link
        (command "rm" [path] context :sudo)
        (when (or is-dir is-file)
          (command "mv" [path (:backup-dir context)] context :sudo)))
      (command "ln" ["-s" src path] context (when sudo :sudo)))
    (when (:executable context)
      (command "chmod" ["+x" path] context :sudo))
    (when owner
      (command "chown" [owner path] context :sudo))))

(defn link
  ([context src dest]
   (link context src dest {}))
  ([context src dest opts]
   (file context dest (merge opts {:state :link :src src}))))

(defn directory
  ([context path]
   (directory context path {}))
  ([context path opts]
   (file context path (assoc opts :state :dir))))

(defn update-content
  [lines index managed-str lines-to-add]
  (if (= -1 index)
    (concat lines (cons managed-str lines-to-add))
    (concat (take index lines) (cons managed-str lines-to-add) (drop (+ 1 (count lines-to-add) index) lines))))

(defn file-content
  [context path description lines-to-add]
  (let [managed-str (str (:managed-str context) description)
        tmp-file (str "/tmp/tfconfig-" (rand))]
    (command "cp" [path tmp-file] (assoc context :preauth true))
    (with-open [reader (io/reader tmp-file)]
      (let [lines (line-seq reader)
            index (.indexOf (or lines []) managed-str)
            new-lines (update-content lines index managed-str lines-to-add)]
        (when (= index -1)
          (with-open [writer (io/writer tmp-file)]
            (dorun (map #(.write writer (str % "\n")) new-lines)))
          (command "mv" [tmp-file path] (assoc context :preauth true) :sudo)
          (notify context "lines"))))))

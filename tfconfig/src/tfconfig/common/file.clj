(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.handler :refer :all]))

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
  [path context]
  (let [desired-state (:state context)
        owner (:owner context)
        src (:src context)
        disabled-errors-context (assoc context :throw-errors false)
        force-sudo-context (assoc context :sudo true)
        is-dir (dir-exists? context path)
        is-file (file-exists? context path)
        is-link (link-exists? context path)]
    (do
      (when (and (= desired-state "dir") (not is-dir))
        (do
          (println (str "Creating directory: " path))
          (command "mkdir" ["-p" path] (assoc context :sudo true))
          (command "chown" [(str (:username context) ":") path] (assoc context :sudo true))
          (notify context "created")))
      (when (= desired-state "link")
        (do
          (if is-link
            (command "rm" [path] force-sudo-context)
            (when (or is-dir is-file)
              (command "mv" [path (:backup-dir context)] force-sudo-context)))
          (command "ln" ["-s" src path] context)))
      (when (:executable context)
        (command "chmod" ["+x" path] context))
      (when owner
        (command "chown" [owner path] context)))))

(defn link
  ([context src dest]
   (link context src dest {}))
  ([context src dest opts]
   (file dest (merge opts (assoc context :src src :state "link")))))

(defn directory
  ([context path]
   (directory context path {}))
  ([context path opts]
   (file path (merge opts (assoc context :state "dir")))))

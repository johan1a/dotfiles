(ns tfconfig.core
  (:require
   [clj-yaml.core :as yaml]
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.command :refer [command]]
   [clojure.string :as string]
   [clojure.java.io :as io])
  (:gen-class))

(defn get-arg-value
  [args argname]
  (when args
    (let [i (.indexOf args argname)]
      (when-not (or (= i -2) (= i (- (count args) 1)))
        (nth args (+ i 1))))))

(defn get-password
  [args]
  (let [password (get-arg-value args "--password")]
    (if password
      password
      (System/getenv "PASSWORD"))))

(defn get-user
  [args]
  (let [user (get-arg-value args "--user")]
    (if user
      user
      (System/getenv "USER"))))

(defn get-config
  [args]
  (let [path (get-arg-value args "--config")]
    (if path (yaml/parse-string (slurp path)) {})))

(def changes (atom {}))

(defn is-module
  [file]
  (clojure.string/includes? (.getAbsolutePath file) "module.clj"))

(defn get-parent-name
  [file]
  (let [parent (.getParent file)]
    (last (clojure.string/split parent #"/"))))

(defn discover-single-file-modules
  [modules-dir]
  (let [files (file-seq (io/file modules-dir))
        top-level-files (filter #(= modules-dir (str (.getParent %) "/")) files)
        module-files (filter #(.endsWith (.getName %) ".clj") top-level-files)]
    (map #(hash-map :file % :name (.replace (.getName %) ".clj" "")) module-files)))

(defn discover-modules
  [modules-dir]
  (let [module-files (filter is-module (file-seq (io/file modules-dir)))
        modules (map #(hash-map :name (get-parent-name %) :file %) module-files)
        single-file-modules (discover-single-file-modules modules-dir)]
    (concat modules single-file-modules)))

(defn find-in-list
  [all-modules module]
  (first (filter #(= (:name %) module) all-modules)))

(defn get-modules-to-run
  [all-modules config profile]
  (let [ignored-modules (:ignored-modules profile)
        additional-modules (:additional-modules profile)
        without-ignored (remove #(.contains ignored-modules %) (:modules config))
        chosen-modules (concat without-ignored additional-modules)]
    (remove nil? (map #(find-in-list all-modules %) chosen-modules))))

(defn get-forced-modules
  [all-modules args]
  (let [value (get-arg-value args "--modules")]
    (if value
      (remove nil? (map #(find-in-list all-modules %) (clojure.string/split value #",")))
      nil)))

(defn run-module
  [module context]
  (let [module-name (:name module)
        run-module (load-file (.getAbsolutePath (:file module)))
        startTime (. System (currentTimeMillis))
        _ (run-module context)
        elapsedMillis (- (. System (currentTimeMillis)) startTime)
        elapsedSeconds (/ elapsedMillis 1000.0)]
    {:elapsed elapsedSeconds :module module-name}))

(defn pattern-matches?
  [pattern-str hostname]
  (re-matches (re-pattern pattern-str) hostname))

(defn get-profile
  [config]
  (let [hostname (first (:stdout (command "hostname" [] {})))
        profiles (:profiles config)]
    (first (filter #(pattern-matches? (:pattern %) hostname) profiles))))

(defn result-string
  [result]
  (str (:module result) ": " (:elapsed result) "s"))

(defn -main
  [& args]
  (println "Setting up context")
  (let [password (get-password args)
        user (get-user args)
        home (<< "/home/~{user}/")
        dotfiles-root (clojure.string/replace (System/getProperty "user.dir") #"/tfconfig" "")
        config (get-config args)
        profile (get-profile config)
        modules-dir (str dotfiles-root "/tfconfig/src/tfconfig/modules/")
        all-modules (discover-modules modules-dir)
        forced-modules (get-forced-modules all-modules args)
        modules-to-run (or forced-modules (get-modules-to-run all-modules config profile))
        context {:home home
                 :root-dir dotfiles-root
                 :modules-dir modules-dir
                 :sources-dir (str home "source/")
                 :backup-dir (str home ".dotfiles_backup")
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 :changes changes
                 :throw-errors true
                 :managed-str "# The following line is managed by tfconfig, do not edit. Description: "
                 :profile profile
                 :os (or (:os profile) "archlinux")
                 :ci (System/getenv "CI")}]
    (if password
      (do
        (println (str "Root dir: " (:root-dir context)))
        (let [results (map #(run-module % context) modules-to-run)
              result-strings (map result-string results)
              summary (string/join "\n" result-strings)]
          (println "Done!")
          (println "")
          (println "Summary:")
          (println summary)))
      (println "No password specified"))
    (shutdown-agents)))

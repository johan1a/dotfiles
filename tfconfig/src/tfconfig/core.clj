(ns tfconfig.core
  (:require [tfconfig.common.command :refer :all]
            [clojure.core.strint :refer [<<]]
            [clj-yaml.core :as yaml])
  (:gen-class))

(defn get-arg-value
  [args argname]
  (if args
    (let [i (.indexOf args argname)]
      (if-not (= i -1)
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

(defn get-modules
  [dir-name]
  (filter is-module (file-seq (clojure.java.io/file dir-name))))

(defn get-parent-name
  [file]
  (let [parent (.getParent file)]
    (last (clojure.string/split parent #"/"))))

(defn find-in-list
  [all-modules module]
  (first (filter #(= (get-parent-name %) module) all-modules)))

(defn get-modules-to-run
  [all-modules config]
  (let [chosen-modules (:modules config)]
    (remove nil? (map #(find-in-list all-modules %) chosen-modules))))

(defn run-module
  [file context]
  (do
    (println (<< "-- Module: ~(get-parent-name file) --"))
    (let [run-module (load-file (.getAbsolutePath file))]
          (run-module context))))

(defn -main
  [& args]
  (println "Setting up context")
  (let [password (get-password args)
        user (get-user args)
        home (<< "/home/~{user}/")
        dotfiles-root (clojure.string/replace (System/getProperty "user.dir") #"/tfconfig" "")
        config (get-config args)

        hostname (first (:stdout (command "hostname" [] {})))
        profile (if (= hostname "PSSE307") "work" "home")
        modules-dir (str dotfiles-root "/tfconfig/src/tfconfig/modules/")
        all-modules (get-modules modules-dir)
        modules-to-run (get-modules-to-run all-modules config)
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
                 :ci (System/getenv "CI")}]
    (do
      (if password
        (do
          (println (str "Root dir: " (:root-dir context)))
          (dorun (map #(run-module % context) modules-to-run))
          (println "Done!"))
        (println "No password specified"))
      (shutdown-agents))))

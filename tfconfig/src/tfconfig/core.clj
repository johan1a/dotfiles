(ns tfconfig.core
  (:require [tfconfig.modules.aur.module :as aur]
            [tfconfig.modules.gpg.module :as gpg])
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

(def changes (atom {}))

(defn -main
  [& args]
  (let [password (get-password args)
        user (get-user args)
        context {:home "/home/johan"
                 :root-dir "/home/johan/dotfiles/"
                 :modules-dir "/home/johan/dotfiles/tfconfig/src/tfconfig/modules/"
                 :sources-dir "/home/johan/source/"
                 :backup-dir "/home/johan/.dotfiles_backup"
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 :changes changes
                 :throw-errors true}]
    (if password
      (do
        (aur/run context)
        (gpg/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

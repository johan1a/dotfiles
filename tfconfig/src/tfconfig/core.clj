(ns tfconfig.core
  (:require [tfconfig.modules.aur.module :as aur]
            [tfconfig.modules.gpg.module :as gpg]
            [tfconfig.modules.neovim.module :as neovim]
            [tfconfig.modules.dirs.module :as dirs]
            [tfconfig.modules.polybar.module :as polybar]
            [tfconfig.modules.i3-gaps.module :as i3-gaps]
            [clojure.core.strint :refer [<<]])
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
        home (<< "/home/~{user}/")
        context {:home home
                 :root-dir (str home "dotfiles/")
                 :modules-dir (str home "dotfiles/tfconfig/src/tfconfig/modules/")
                 :sources-dir (str home "source/")
                 :backup-dir (str home ".dotfiles_backup")
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 :changes changes
                 :throw-errors true
                 :ci (System/getenv "CI")}]
    (if password
      (do
        (dirs/run context)
        (i3-gaps/run context)
        (neovim/run context)
        (aur/run context)
        (polybar/run context)
        (gpg/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

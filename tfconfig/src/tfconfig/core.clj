(ns tfconfig.core
  (:require [tfconfig.modules.aur.module :as aur]
            [tfconfig.modules.gpg.module :as gpg]
            [tfconfig.modules.neovim.module :as neovim]
            [tfconfig.modules.dirs.module :as dirs]
            [tfconfig.modules.packages.module :as packages]
            [tfconfig.modules.mlocate.module :as mlocate]
            [tfconfig.modules.polybar.module :as polybar]
            [tfconfig.modules.i3-gaps.module :as i3-gaps]
            [tfconfig.modules.owncloud.module :as owncloud]
            [tfconfig.modules.intellij.module :as intellij]
            [tfconfig.modules.dunst.module :as dunst]
            [tfconfig.modules.terminal.module :as terminal]
            [tfconfig.modules.fzf.module :as fzf]
            [tfconfig.modules.colemak.module :as colemak]
            [tfconfig.modules.tmux.module :as tmux]
            [tfconfig.modules.git.module :as git]
            [tfconfig.modules.mlocate.module :as mlocate]
            [tfconfig.modules.xorg.module :as xorg]
            [tfconfig.modules.ctags.module :as ctags]
            [tfconfig.modules.taskwarrior.module :as taskwarrior]
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
        dotfiles-root (clojure.string/replace (System/getProperty "user.dir") #"/tfconfig" "")
        context {:home home
                 :root-dir dotfiles-root
                 :modules-dir (str dotfiles-root "/tfconfig/src/tfconfig/modules/")
                 :sources-dir (str home "source/")
                 :backup-dir (str home ".dotfiles_backup")
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 :changes changes
                 :throw-errors true
                 :managed-str "The following line is managed by tfconfig, do not edit. Description: "
                 :ci (System/getenv "CI")}]
    (if password
      (do
        (println (str "Root dir: " (:root-dir context)))
        (dirs/run context)
        (packages/run context)
        (mlocate/run context)
        (git/run context)
        (ctags/run context)
        (mlocate/run context)
        (intellij/run context)
        (owncloud/run context)
        (taskwarrior/run context)
        (i3-gaps/run context)
        (neovim/run context)
        (aur/run context)
        (xorg/run context)
        (terminal/run context)
        (fzf/run context)
        (tmux/run context)
        (colemak/run context)
        (dunst/run context)
        (polybar/run context)
        (gpg/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

(ns tfconfig.modules.terminal.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.aur :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn set-default-shell
  [context]
  (command "chsh" ["-s" "/usr/bin/fish"] context))

(defn link-files
  [context]
  (do
    (let [src-dir (<< "~(:modules-dir context)terminal/files")
          functions-dir (str src-dir "/functions")
          dest-dir (str (:home context) ".config/fish")]
    (link context (<< "~{functions-dir}/fish_mode_prompt.fish") (<< "~{dest-dir}/functions/fish_mode_prompt.fish"))
    (link context (<< "~{functions-dir}/fish_prompt.fish") (<< "~{dest-dir}/functions/fish_prompt.fish"))
    (link context (<< "~{functions-dir}/utils.fish") (<< "~{dest-dir}/functions/utils.fish"))
    (link context (<< "~{functions-dir}/fish_user_key_bindings.fish") (<< "~{dest-dir}/functions/fish_user_key_bindings.fish"))
    (link context (<< "~{src-dir}/config.fish") (<< "~{dest-dir}/config.fish"))
    )))

(defn set-permissions
  [context]
  (file (<< "~(:home context).config/fish") (assoc context :owner (str (:username context) ":"))))

(defn setup-fish
  [context]
  (do
    (handler context :installed-fish set-default-shell)
    (pacman "fish" (assoc context :handler-ref :installed-fish))
    (link context (<< "~(:modules-dir context)terminal/files/bashrc") (str (:home context) ".bashrc"))
    (link context (<< "~(:modules-dir context)terminal/files/inputrc") (str (:home context) ".inputrc"))
    (link context (<< "~(:modules-dir context)terminal/files/aliases.sh") (str (:home context) ".aliases.sh"))
    (directory context (<< "~(:home context).config/fish/functions"))
    (link-files context)
    (set-permissions context)))

(defn setup-termite
  [context]
  (do
    (directory context (<< "~(:home context).config/termite"))
    (link context (<< "~(:modules-dir context)terminal/files/config_termite") (str (:home context) ".config/termite/config"))
    (when-not (has-executable? context "termite")
               (paru context "termite-git")))) ; TODO install community/termite instead?

(defn setup-base16
  [context]
  (let [repo "https://github.com/johan1a/base16-shell.git"
        dest-dir (<< "~(:home context).config/base16-shell")]
    (when-not (dir-exists? context dest-dir)
        (command "git" ["clone" repo dest-dir] context))))

(defn setup-xresources
  [context]
  (do
    (let [src-file (<< "~(:modules-dir context)terminal/files/Xresources")
          dest-file (str (:home context) ".Xresources")]
    (link context src-file dest-file)
    (command "xrdb" ["-merge" dest-file] context))))

(defn setup-gtk3
  [context]
  (do
    (let [base-dir (<< "~(:home context).config/gtk-3.0")
          src-file (<< "~(:modules-dir context)terminal/files/gtk.css")
          dest-file (str base-dir "/gtk.css")]
    (when-not (file-exists? context dest-file)
      (directory context base-dir)
      (link context src-file dest-file)))))

(defn run
  [context]
  (do
    (println "-- Module: terminal --")
    (setup-fish context)
    (setup-termite context)
    (setup-base16 context)
    (setup-xresources context)
    (setup-gtk3 context)))


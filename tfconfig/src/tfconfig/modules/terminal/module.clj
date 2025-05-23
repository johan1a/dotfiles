(ns tfconfig.modules.terminal.module
  (:require [tfconfig.common.apt :as apt]
            [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [link directory file-exists? dir-exists?]]
            [tfconfig.common.pacman :refer [pacman]]
            [tfconfig.common.aur :refer [aur-packages]]
            [clojure.core.strint :refer [<<]]))

(defn set-default-shell
  [context changes]
  (command "chsh" ["-s" "/usr/bin/fish"] (assoc context :input (:password context))))

(defn link-files
  [context]
  (let [src-dir (<< "~(:modules-dir context)terminal/files")
        functions-dir (str src-dir "/functions")
        dest-dir (str (:home context) ".config/fish")]
    (directory (assoc context :owner (str (:username context) ":") :sudo true) dest-dir)
    (link context (<< "~{functions-dir}/fish_mode_prompt.fish") (<< "~{dest-dir}/functions/fish_mode_prompt.fish"))
    (link context (<< "~{functions-dir}/fish_prompt.fish") (<< "~{dest-dir}/functions/fish_prompt.fish"))
    (link context (<< "~{functions-dir}/utils.fish") (<< "~{dest-dir}/functions/utils.fish"))
    (link context (<< "~{functions-dir}/fish_user_key_bindings.fish") (<< "~{dest-dir}/functions/fish_user_key_bindings.fish"))
    (link context (<< "~{src-dir}/config.fish") (<< "~{dest-dir}/config.fish"))))

(defn set-permissions
  [context]
  (directory context (<< "~(:home context).config/fish") {:owner (str (:username context) ":")}))

(defn setup-fish
  [context]
    ; (handler context :installed-fish set-default-shell) ; FIXME it does not enter the password correctly, just hangs
  (let [os (:os context)]
    (when (= os "archlinux")
      (pacman "fish" context :present))
    (when (= os "raspbian")
      (apt/install context ["fish"]))
    (link context (<< "~(:modules-dir context)terminal/files/bashrc") (str (:home context) ".bashrc"))
    (link context (<< "~(:modules-dir context)terminal/files/inputrc") (str (:home context) ".inputrc"))
    (link context (<< "~(:modules-dir context)terminal/files/aliases.sh") (str (:home context) ".aliases.sh"))
    (directory context (<< "~(:home context).config/fish/functions"))
    (directory context (<< "~(:home context).kube"))
    (command "touch" (<< "~(:home context).kube/config") context)
    (link-files context)
    (set-permissions context)))

(defn setup-alacritty
  [context]
  (directory context (<< "~(:home context).config/alacritty"))
  (link context (<< "~(:modules-dir context)terminal/files/alacritty.toml") (str (:home context) ".config/alacritty/alacritty.toml"))
  (aur-packages context "alacritty"))

(defn setup-base16
  [context]
  (let [repo "https://github.com/johan1a/base16-shell.git"
        dest-dir (<< "~(:home context).config/base16-shell")]
    (when-not (dir-exists? context dest-dir)
      (command "git" ["clone" repo dest-dir] context))))

(defn setup-bat
  [context]
  (directory context (<< "~(:home context).config/bat"))
  (link context (<< "~(:modules-dir context)terminal/files/config_bat") (str (:home context) ".config/bat/config")))

(defn setup-gtk3
  [context]
  (let [base-dir (<< "~(:home context).config/gtk-3.0")
        src-file (<< "~(:modules-dir context)terminal/files/gtk.css")
        dest-file (str base-dir "/gtk.css")]
    (when-not (file-exists? context dest-file)
      (directory context base-dir)
      (link context src-file dest-file))))

(defn setup-defaults
  [context]
  (let [dest-file (<< "~(:home context).config/mimeapps.list")
        src-file (<< "~(:modules-dir context)terminal/files/mimeapps.list")]
    (link context src-file dest-file)))

(defn run
  [context]
  (let [os (:os context)]
    (setup-fish context)
    (when (= os "archlinux")
      (setup-alacritty context))
    (setup-base16 context)
    (setup-bat context)
    (setup-gtk3 context)
    (setup-defaults context)))


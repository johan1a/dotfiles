(ns tfconfig.modules.neovim.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.gem :refer :all]
            [tfconfig.common.pip :refer :all]))

(def required-gems ["msgpack" "rdoc" "neovim" "multi_json"])

(defn install-neovim
  [context]
  (pacman "neovim" (assoc context :state "present")))

(defn install-gems
  [context]
  (dorun (map #(gem context % "present") required-gems)))

(defn install-neovim-pip-package
  [context]
  (pip context "neovim" "present"))

(defn install-make
  "Make is required for the msgpack gem"
  [context]
  (pacman "make" (assoc context :state "present")))

(defn create-vim-dir
  [context base-dir]
  (file base-dir (assoc context :state "dir")))

(defn link-configs
  "Symlink init.vim and .vimrc"
  [context nvim-base-dir]
  (let [module-files-dir (str (:modules-dir context) "neovim/files/")
        init-vim-src (str module-files-dir "init.vim")
        init-vim-dest (str nvim-base-dir "init.vim")
        vimrc-dest (str (:home context) ".vimrc")
        coc-settings-src (str module-files-dir "coc-settings.json")
        coc-settings-dest (str nvim-base-dir "coc-settings.json")]
    (link context init-vim-src init-vim-dest)
    (link context init-vim-src vimrc-dest)
    (link context coc-settings-src coc-settings-dest)))

(defn install-plugins
  [context]
  (do
    (when-not (or false (:ci context))
      (command "nvim" ["+PlugInstall" "+qall"] context))
    (command "nvim" ["+UpdateRemotePlugins" "+qall"] context)))

(defn link-custom-snippets
  [context nvim-dir]
  (let [src-file (str (:modules-dir context) "neovim/files/custom_snippets")
        dest-file (str nvim-dir "custom_snippets")]
    (link context src-file dest-file)))

(defn run
  [context]
  (do
    (println "-- Module: neovim --")
    (install-neovim context)
    (install-make context)
    (install-gems context)
    (install-neovim-pip-package context)
    (let [nvim-dir (str (:home context) ".config/nvim/")]
      (create-vim-dir context nvim-dir)
      (link-configs context nvim-dir)
      (install-plugins context)
      (link-custom-snippets context nvim-dir))))

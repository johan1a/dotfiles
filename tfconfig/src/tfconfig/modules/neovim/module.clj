(ns tfconfig.modules.neovim.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.handler :refer :all])
  (:require [tfconfig.common.gem :refer :all])
  (:require [tfconfig.common.pip :refer :all]))

(def required-gems ["msgpack" "rdoc" "neovim" "multi_json"])

(defn install-neovim
  [context]
  (when-not (has-executable? context "nvim")
    (do
      (println "Installing neovim")
      (pacman "neovim" (assoc context :state "present")))))

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
    (file init-vim-dest (assoc context :state "link" :src init-vim-src))
    (file vimrc-dest (assoc context :state "link" :src init-vim-src))
    (file coc-settings-dest (assoc context :state "link" :src coc-settings-src))))

(defn install-plugins
  [context]
  (command "nvim" ["+PluginInstall" "qall"] context)
  (command "nvim" ["+UpdateRemotePlugins" "qall"] context))

(defn run
  [context]
  (do
    (println "-- Module: Neovim --")
    (install-neovim context)
    (install-make context)
    (install-gems context)
    (install-neovim-pip-package context)
    (let [base-dir (str (:home context) ".config/nvim/")]
      (create-vim-dir context base-dir)
      (link-configs context base-dir))
    (install-plugins context)))

; WARNING:  You don't have /home/johan/.gem/ruby/2.7.0/bin in your PATH,
;           gem executables will not run.

  ; - name: Install plugins
  ;   become_user: "{{ username }}"
  ;   command: nvim +PluginInstall +qall
  ;   when: not ci

  ; - name: Update remote plugins
  ;   become_user: "{{ username }}"
  ;   command: nvim +UpdateRemotePlugins +qall
  ;   when: not ci

  ; - name: Check for LanguageClient-neovim
  ;   stat:
  ;     path: "{{ user_home }}/.vim/bundle/LanguageClient-neovim/bin/languageclient"
  ;   register: languageclient

  ; - name: Install LanguageClient-neovim
  ;   command: sh install.sh
  ;   become_user: "{{ username }}"
  ;   args:
  ;     chdir: "{{ user_home }}/.vim/bundle/LanguageClient-neovim"
  ;   when: not languageclient.stat.exists
  ;   failed_when: false

  ; - name: Setup permissions
  ;   file:
  ;     path: "{{ user_home }}/.vim"
  ;     owner: "{{ username }}"
  ;     recurse: yes

  ; - name: install neovim gem
  ;   command: gem install neovim
  ;   become_user: "{{ username }}"

  ; - name: Symlink custom_snippets
  ;   script: >
  ;         scripts/install_dotfile
  ;         "{{ dotfiles_dir }}/ansible/roles/neovim/templates/custom_snippets"
  ;         "{{ user_home }}/.vim/custom_snippets"

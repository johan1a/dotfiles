(ns tfconfig.modules.neovim.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.handler :refer :all])
  (:require [tfconfig.common.gem :refer :all]))

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

(defn run
  [context]
  (do
    (println "-- Module: Neovim --")
    (install-neovim context)
    (install-gems context)
    (install-neovim-pip-package context)))

; WARNING:  You don't have /home/johan/.gem/ruby/2.7.0/bin in your PATH,
;           gem executables will not run.

  ; - name: Install neovim pip3 package
  ;   pip:
  ;     name: neovim
  ;     state: latest
  ;     executable: pip3
  ;     extra_args: --user
  ;   become_user: "{{ username }}"

  ; - name: Make sure .vim exists
  ;   file:
  ;     path: "{{ user_home }}/.vim"
  ;     state: directory
  ;     owner: "{{ username }}"

  ; - name: Link neovim to vim config
  ;   script: >
  ;         scripts/install_dotfile
  ;         "{{ user_home }}/.vim"
  ;         "{{ user_home }}/.config/nvim"

  ; - name: Symlink .vimrc
  ;   script: >
  ;         scripts/install_dotfile
  ;         "{{ dotfiles_dir }}/ansible/roles/neovim/templates/vimrc"
  ;         "{{ user_home }}/{{ item }}"
  ;   with_items:
  ;     - .vimrc
  ;     - .vim/init.vim

  ; - name: Symlink .coc-settings.json
  ;   script: >
  ;         scripts/install_dotfile
  ;         "{{ dotfiles_dir }}/ansible/roles/neovim/templates/coc-settings.json"
  ;         "{{ user_home }}/.vim/coc-settings.json"

  ; - name: Check if Vundle exists
  ;   stat: path="{{ user_home }}/.vim/bundle/Vundle.vim"
  ;   register: vundle_dir

  ; - name: Install Vundle
  ;   git: repo=https://github.com/VundleVim/Vundle.vim.git
  ;        dest="{{ user_home }}/.vim/bundle/Vundle.vim"
  ;        accept_hostkey=yes
  ;   when: not vundle_dir.stat.exists

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

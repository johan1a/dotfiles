(ns tfconfig.modules.neovim.module
  (:require
   [tfconfig.common.apt :as apt]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.file :refer [directory link]]
   [tfconfig.common.gem :refer [gem]]
   [tfconfig.common.pacman :refer [pacman]]
   [tfconfig.common.has-executable :refer [has-executable?]]))

(def required-gems ["msgpack" "rdoc" "neovim" "multi_json"])

(defn install-neovim
  [context]
  (let [os (:os context)]
    (when (= os "archlinux")
      (command "paru" ["-R" "--noconfirm" "--sudoloop" "neovim-nightly-bin"] (assoc context :pre-auth true :throw-errors false))
      (pacman "neovim" context :present)
      (pacman "python-neovim" context :present)
      (when-not (has-executable? context "vls")
        (command "npm" ["install" "-g" "vue-language-server"] context :sudo)))
    (when (= os "raspbian")
      (apt/install (assoc context :throw-errors false) ["neovim"]))))

(defn install-gems
  [context]
  (dorun (map #(gem (assoc context :throw-errors false) % "present") required-gems)))

(defn install-make
  "Make is required for the msgpack gem"
  [context]
  (let [os (:os context)]
    (when (= os "archlinux")
      (pacman "make" context :present))
    (when (= os "raspbian")
      (apt/install context ["make"]))))

(defn create-vim-dir
  [context base-dir]
  (directory context base-dir))

(defn link-configs
  "Symlink init.vim"
  [context nvim-base-dir]
  (let [module-files-dir (str (:modules-dir context) "neovim/files/")
        init-vim-dest (str nvim-base-dir "init.lua")
        init-vim-src (str module-files-dir "init.lua")]
    (link context init-vim-src init-vim-dest)))

(defn install-plugins
  [context]
  (when-not (:ci context)
    (command "nvim" ["--headless" "+PlugInstall" "+qall"] context)
    (command "nvim" ["--headless" "+UpdateRemotePlugins" "+qall"] context)))

(defn link-custom-snippets
  [context nvim-dir]
  (let [src-file (str (:modules-dir context) "neovim/files/custom_snippets")
        dest-file (str nvim-dir "custom_snippets")]
    (link context src-file dest-file)))

(defn run
  [context]
  (let [os (:os context)]
    (install-neovim context)
    (install-make context)
    (when (= os "archlinux")
      (install-gems context))
    (let [nvim-dir (str (:home context) ".config/nvim/")]
      (create-vim-dir context nvim-dir)
      (link-configs context nvim-dir)
      (link-custom-snippets context nvim-dir))))

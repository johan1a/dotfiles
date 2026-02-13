(ns tfconfig.modules.packages-brew.module
  (:require
   [tfconfig.common.brew :as brew]))

; brew leaves --installed-on-request

(def packages ["colima"
               "clojure-lsp"
               "docker"
               "docker-compose"
               "fish"
               "fzf"
               "helm"
               "httpie"
               "jq"
               "kubernetes-cli"
               "leiningen"
               "md5sha1sum"
               "neovim"
               "openjdk@11"
               "openjdk@17"
               "rectangle"
               "ripgrep"
               "the_silver_searcher"
               "tmux"
               "tree"
               "universal-ctags"
               "watch"
               "yq"
               "yapf"
               "mysql-client"
               "grpcurl"
               "kubeseal"
               "reattach-to-user-namespace" ; for tmux copy past in macos
               "mysql-client"
               "stern"])

(defn run
  "Installs useful packages"
  [context]
  (brew/install context packages))

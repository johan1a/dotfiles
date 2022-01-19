(ns tfconfig.modules.packages-apt.module
  (:require
   [tfconfig.common.apt :as apt]))

(def packages ["fzf" "ripgrep" "silversearcher-ag" "tmux" "universal-ctags"])

(defn run
  "Installs useful packages"
  [context]
  (let [sudo-context (assoc context :sudo true)]
    (apt/install sudo-context packages)))

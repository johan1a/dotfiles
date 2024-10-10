(ns tfconfig.modules.tmux.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [link]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (when (= (:os context) "archlinux")
    (pacman "tmux" context :present))
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)tmux/files")]
    (link context (str files-dir "/tmux.conf") (str home ".tmux.conf"))
    (link context (str files-dir "/tmux.reset.conf") (str home ".tmux.reset.conf"))))

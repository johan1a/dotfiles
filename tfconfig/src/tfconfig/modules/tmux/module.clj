(ns tfconfig.modules.tmux.module
  (:require [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (do
      (pacman "tmux" (assoc context :state "present"))
      (let [home (:home context)
            files-dir (<< "~(:modules-dir context)tmux/files")]
        (link context (str files-dir "/tmux.conf") (str home ".tmux.conf"))
        (link context (str files-dir "/tmux.reset.conf") (str home ".tmux.reset.conf"))))))

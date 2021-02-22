(ns tfconfig.modules.taskwarrior.module
  (:require [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (do
      (pacman "task" (assoc context :state "present"))
      (dorun (file (<< "~{home}/ownCloud/task") (assoc context :state "dir")))
      (link context (<< "~{home}/ownCloud/task") (<< "~{home}/.task")))))

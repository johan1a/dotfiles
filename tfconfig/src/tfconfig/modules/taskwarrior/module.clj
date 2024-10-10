(ns tfconfig.modules.taskwarrior.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [directory link]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (let [home (:home context)]
    (pacman "task" context :present)
    (dorun (directory context (<< "~{home}/ownCloud/task")))
    (link context (<< "~{home}/ownCloud/task") (<< "~{home}/.task"))))

(ns tfconfig.modules.taskwarrior.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [file link]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (let [home (:home context)]
    (pacman "task" context :present)
    (dorun (file (<< "~{home}/ownCloud/task") (assoc context :state "dir")))
    (link context (<< "~{home}/ownCloud/task") (<< "~{home}/.task"))))

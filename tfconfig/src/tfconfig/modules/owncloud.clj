(ns tfconfig.modules.owncloud
  (:require [tfconfig.common.file :refer [link]]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (link context (<< "~{home}ownCloud/documents") (<< "~{home}documents"))
    (link context (<< "~{home}ownCloud/pictures") (<< "~{home}pictures"))))

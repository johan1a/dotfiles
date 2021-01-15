(ns tfconfig.modules.owncloud.module
  (:require [tfconfig.common.file :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (do
      (println "-- Module: Owncloud --")
      (link context (<< "~{home}/owncloud/documents") (<< "~{home}/documents"))
      (link context (<< "~{home}/owncloud/vimwiki") (<< "~{home}/vimwiki")))))

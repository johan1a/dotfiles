(ns tfconfig.modules.owncloud.module
  (:require [tfconfig.common.file :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (do
      (println "-- Module: owncloud --")
      (when-not (= (:profile context) "work")
        (do
          (link context (<< "~{home}ownCloud/documents") (<< "~{home}documents"))
          (link context (<< "~{home}ownCloud/vimwiki") (<< "~{home}vimwiki")))))))

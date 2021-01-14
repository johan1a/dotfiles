(ns tfconfig.modules.dirs.module
  (:require [tfconfig.common.file :refer :all]))




(defn run
  "Create standard directories"
  [context]
  (println "-- Module: dirs --")
  (let [home (:home context)
        dirs [(:sources-dir context)
              (:backup-dir context)
              (str home ".local/bin")
              (str home ".local/lib")
              (str home ".config")]]
    (dorun (map #(file % (assoc context :state "dir" :owner (:username context))) dirs))))

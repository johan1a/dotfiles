(ns tfconfig.modules.dirs.module
  (:require [tfconfig.common.file :refer :all]))




(defn run
  "Create standard directories"
  [context]
  (println "-- Module: dirs --")
  (let [dirs [(:sources-dir context)
              (:backup-dir context)
              (:bin-dir context)
              (:config-dir context)]]
    (dorun (map #(file % (assoc context :state "dir" :owner (:username context))) dirs))))

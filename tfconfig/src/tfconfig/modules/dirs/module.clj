(ns tfconfig.modules.dirs.module
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.command :refer :all]))

(defn run
  "Create standard directories"
  [context]
  (println "-- Module: dirs --")
  (let [home (:home context)
        owner (str (:username context) ":")
        dirs [(:sources-dir context)
              (:backup-dir context)
              (str home ".local")
              (str home ".local/bin")
              (str home ".local/lib")
              (str home ".config")]]
    (dorun (map #(file % (assoc context :state "dir" :owner owner)) dirs))))

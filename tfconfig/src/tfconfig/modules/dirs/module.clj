(ns tfconfig.modules.dirs.module
  (:require [tfconfig.common.file :refer :all]))




(defn run
  "Create standard directories"
  [context]
  (let [dirs [(:sources-dir context)
              (:backup-dir context)
              (:bin-dir context)]]
    (map #(file % (assoc context :state "dir")) dirs)))

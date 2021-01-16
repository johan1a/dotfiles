(ns tfconfig.modules.intellij.module
  (:require [tfconfig.common.file :refer :all]))

(defn run
  [context]
    (println "-- Module: Intellij")
    (link context (str (:modules-dir context) "intellij/files/ideavimrc") (str (:home context) ".ideavimrc")))

(ns tfconfig.modules.packages.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [clojure.core.strint :refer [<<]]))

(def packages ["base-devel" "autoconf"])

(defn run
  "Installs useful packages"
  [context]
  (do
    (println "-- Module: packages --")
    (dorun (map #(pacman % (assoc context :state "present")) packages))))

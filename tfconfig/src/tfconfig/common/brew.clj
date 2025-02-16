(ns tfconfig.common.brew
  (:require [tfconfig.common.command :refer [command]]))

(defn install
  [context packages]
  (command "brew" (into [] ["install" packages]) context))

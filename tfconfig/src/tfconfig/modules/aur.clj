(ns tfconfig.modules.aur
  (:require [tfconfig.common.command :refer :all]))

(defn install-paru
  []
  (command "ls" "-l"))

(defn run
  "I don't do a whole lot ... yet."
  [& args]
  (install-paru))

(ns tfconfig.modules.dunst.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.aur :refer [aur-packages]]
   [tfconfig.common.file :refer [directory link]]))

(defn run
  [context]
  (aur-packages context "dunst")
  (directory context (<< "~(:home context).config/dunst"))
  (link context (<< "~(:modules-dir context)dunst/files/dunstrc") (<< "~(:home context).config/dunst/dunstrc")))

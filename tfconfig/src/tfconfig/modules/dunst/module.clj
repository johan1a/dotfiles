(ns tfconfig.modules.dunst.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.aur :refer [install-aur-package]]
   [tfconfig.common.file :refer [directory link]]))

(defn run
  [context]
  (install-aur-package context "dunst")
  (directory context (<< "~(:home context).config/dunst"))
  (link context (<< "~(:modules-dir context)dunst/files/dunstrc") (<< "~(:home context).config/dunst/dunstrc")))

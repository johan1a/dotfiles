(ns tfconfig.modules.dunst.module
  (:require
   [tfconfig.common.file :refer :all]
   [tfconfig.common.command :refer :all]
   [tfconfig.common.pacman :refer :all]
   [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (println "-- Module: dunst --")
    (command "paru" ["-S" "--needed" "--noconfirm" "--sudoloop" "dunst"] context)
    (directory context (<< "~(:home context).config/dunst"))
    (link context (<< "~(:modules-dir context)dunst/files/dunstrc") (<< "~(:home context).config/dunst/dunstrc"))))

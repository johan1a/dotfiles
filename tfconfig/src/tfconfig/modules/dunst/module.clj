(ns tfconfig.modules.dunst.module
  (:require
   [tfconfig.common.file :refer :all]
   [tfconfig.common.command :refer :all]
   [tfconfig.common.pacman :refer :all]
   [tfconfig.common.aur :refer :all]
   [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (install-aur-package context "dunst")
    (directory context (<< "~(:home context).config/dunst"))
    (link context (<< "~(:modules-dir context)dunst/files/dunstrc") (<< "~(:home context).config/dunst/dunstrc"))))

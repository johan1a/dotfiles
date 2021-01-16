(ns tfconfig.modules.neovim.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (println "-- Module: colemak --")
    (let [src (<< "~(:modules-dir context)colemak/files/colemaknordic")
          dest "/usr/share/X11/xkb/symbols/colemaknordic"]
             (link (assoc context :sudo true) src dest)
             (command "setxkbmap" ["colemaknordic" "-option" "altwin:swap_lalt_lwin" "-option" "caps:super"] context))))

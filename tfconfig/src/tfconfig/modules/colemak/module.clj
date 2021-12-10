(ns tfconfig.modules.colemak.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (let [src (<< "~(:modules-dir context)colemak/files/colemaknordic")
          dest "/usr/share/X11/xkb/symbols/colemaknordic"
          vconsole-src (<< "~(:modules-dir context)colemak/files/vconsole.conf")
          vconsole-dest "/etc/vconsole.conf"]
      (link (assoc context :sudo true) src dest)
      (link (assoc context :sudo true) vconsole-src vconsole-dest)
      (when (:has-monitor (:profile context))
        (command "setxkbmap" ["colemaknordic" "-option" "altwin:swap_lalt_lwin" "-option" "caps:super"] context)))))

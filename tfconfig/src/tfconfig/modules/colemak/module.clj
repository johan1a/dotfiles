(ns tfconfig.modules.colemak.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [link]]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [src (<< "~(:modules-dir context)colemak/files/colemaknordic")
        dest "/usr/share/X11/xkb/symbols/colemaknordic"
        vconsole-src (<< "~(:modules-dir context)colemak/files/vconsole.conf")
        vconsole-dest "/etc/vconsole.conf"]
    (link (assoc context :sudo true) src dest)
    (link (assoc context :sudo true) vconsole-src vconsole-dest)
    (when (:has-monitor (:profile context))
      (command "setxkbmap" ["colemaknordic" "-option" "altwin:swap_lalt_lwin" "-option" "caps:super"] context))))

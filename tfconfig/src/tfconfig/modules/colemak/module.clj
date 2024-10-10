(ns tfconfig.modules.colemak.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [link]]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [colemaknordic-src (<< "~(:modules-dir context)colemak/files/colemaknordic")
        colemaknordic-dest "/usr/share/X11/xkb/symbols/colemaknordic"
        vconsole-src (<< "~(:modules-dir context)colemak/files/vconsole.conf")
        vconsole-dest "/etc/vconsole.conf"
        xorg-config-src (<< "~(:modules-dir context)colemak/files/00-keyboard.conf")
        xorg-config-dest "/etc/X11/xorg.conf.d/00-keyboard.conf"]
    (link context colemaknordic-src colemaknordic-dest { :sudo true })
    (link context vconsole-src vconsole-dest { :sudo true })
    (link context xorg-config-src xorg-config-dest { :sudo true })
    (when (:has-monitor (:profile context))
      (command "setxkbmap" ["-layout" "us" "-variant" "colemak" "-option" "altwin:swap_lalt_lwin" "-option" "ctrl:swapcaps"] context))))

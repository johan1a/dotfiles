(ns tfconfig.modules.xorg.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.file :refer [link]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn setup-xorg
  [context]
  (let [xresources-src (<< "~(:modules-dir context)xorg/files/Xresources")
        xresources-dest (str (:home context) ".Xresources")
        xinitrc-src (<< "~(:modules-dir context)xorg/files/xinitrc")
        xinitrc-dest (str (:home context) ".xinitrc")]
    (pacman "xorg" context :present)
    (pacman "xorg-xrandr" context :present)
    (pacman "xorg-server" context :present)
    (pacman "xorg-xinit" context :present)
    (link context xresources-src xresources-dest)
    (link context xinitrc-src xinitrc-dest)
    (link context (<< "~(:modules-dir context)xorg/files/10-serverflags.conf") "/etc/X11/xorg.conf.d/10-serverflags.conf" {:sudo true})
    (when (:has-monitor (:profile context))
      (command "xrdb" ["-merge" xresources-dest] context))))

(defn run
  [context]
  (setup-xorg context))

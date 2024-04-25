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
        xinitrc-dest (str (:home context) ".xinitrc")
        pacman-state-present (assoc context :state "present")]
    (pacman "xorg" pacman-state-present)
    (pacman "xorg-xrandr" pacman-state-present)
    (pacman "xorg-server" pacman-state-present)
    (pacman "xorg-xinit" pacman-state-present)
    (link context xresources-src xresources-dest)
    (link context xinitrc-src xinitrc-dest)
    (link (assoc context :sudo true) (<< "~(:modules-dir context)xorg/files/10-serverflags.conf") "/etc/X11/xorg.conf.d/10-serverflags.conf")
    (when (:has-monitor (:profile context))
      (command "xrdb" ["-merge" xresources-dest] context))))

(defn run
  [context]
  (setup-xorg context))

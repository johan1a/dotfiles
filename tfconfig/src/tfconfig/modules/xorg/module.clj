(ns tfconfig.modules.xorg.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn setup-xorg
  [context]
  (do
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
      (when (:has-monitor (:profile context))
        (command "xrdb" ["-merge" xresources-dest] context)))))

(defn run
  [context]
  (setup-xorg context))

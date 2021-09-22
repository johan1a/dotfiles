(ns tfconfig.modules.pulseaudio.module
  (:require [tfconfig.common.aur :refer [install-aur-packages]]))

(def packages
  ["pulseaudio"
   "pulseaudio-alsa"
   "pulseaudio-bluetooth"
   "pulseaudio-jack"
  ])

(defn run
  "Installs Pulseaudio"
  [context]
  (do
    (install-aur-packages context packages)))

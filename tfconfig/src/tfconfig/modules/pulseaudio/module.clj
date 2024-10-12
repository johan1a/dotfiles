(ns tfconfig.modules.pulseaudio.module
  (:require [tfconfig.common.aur :refer [aur-packages]]))

(def packages
  ["pulseaudio"
   "pulseaudio-alsa"
   "pulseaudio-bluetooth"
   "pulseaudio-jack"])

(defn run
  "Installs Pulseaudio"
  [context]
  (aur-packages context packages))

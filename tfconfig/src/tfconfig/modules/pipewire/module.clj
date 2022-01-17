(ns tfconfig.modules.pipewire.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.aur :refer [install-aur-packages]]))

(def packages
  ["pipewire-pulse" ; For more bluetooth headset codecs
   "pipewire-alsa" ; Not sure if needed
   "libldac" ; Not sure if needed
   ])

(defn run
  "Installs Pipewire"
  [context]
  (install-aur-packages context packages)
  (command "systemctl" ["--user" "enable" "pipewire.service" "pipewire-pulse.service"] context))

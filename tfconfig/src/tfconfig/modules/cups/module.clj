(ns tfconfig.modules.cups.module
  (:require
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.aur :refer [install-aur-package]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (pacman "cups" context :present)
  (pacman "cups-pdf" context :present)
  (install-aur-package (assoc context :throw-errors false) "cnijfilter-common")  ; For cups and the Canon pixma iP2850 printer
  (command "usermod" ["-a" "-G" "sys" (:username context)] context :sudo)
  (when-not (:ci context)
    (command "systemctl" ["enable" "cups.service"] context :sudo)
    (command "systemctl" ["start" "cups.service"] context :sudo)))

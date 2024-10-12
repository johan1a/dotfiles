(ns tfconfig.modules.forensic-tools
  (:require [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (pacman ["zsteg"
           "arp-scan"
           "sleuthkit"
           "stegsolve"] context))

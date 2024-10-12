(ns tfconfig.modules.forensic-tools
  (:require [tfconfig.common.aur :refer [aur-packages]]))

(defn run
  [context]
  (aur-packages ["zsteg"
                 "arp-scan"
                 "sleuthkit"
                 "stegsolve"] context))

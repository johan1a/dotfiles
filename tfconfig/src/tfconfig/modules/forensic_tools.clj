(ns tfconfig.modules.forensic-tools
  (:require [tfconfig.common.aur :refer [aur-packages]]))

(defn run
  [context]
  (aur-packages context ["zsteg"
                         "arp-scan"
                         "sleuthkit"
                         "stegsolve"]))

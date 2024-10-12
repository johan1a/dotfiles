(ns tfconfig.modules.forensic-tools
  (:require [tfconfig.common.pacman :refer [pacman]]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (let [home (:home context)]
    (pacman ["zsteg"
             "arp-scan"
             "sleuthkit"
             "stegsolve"] context)))

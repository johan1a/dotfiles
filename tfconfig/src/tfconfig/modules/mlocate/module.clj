(ns tfconfig.modules.mlocate.module
  (:require
   [tfconfig.common.cron :refer [cron]]
   [tfconfig.common.pacman :refer [pacman]]
   [tfconfig.common.file :refer [directory]]))

(defn run
  [context]
    (pacman "mlocate" (assoc context :state "present"))
    (directory context (str (:home context) ".config/mlocate"))
    (cron context "Run updatedb" "*/15 * * * * updatedb -l 0 -o ~/.config/mlocate/mlocate.db >> ~/.local/log/updatedb.log 2>&1"))

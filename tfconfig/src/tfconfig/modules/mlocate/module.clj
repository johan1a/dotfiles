(ns tfconfig.modules.mlocate.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.aur :refer :all]
            [tfconfig.common.cron :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (println "-- Module: mlocate --")
    (pacman "mlocate" (assoc context :state "present"))
    (directory context (str (:home context) ".config/mlocate"))
    (cron context "Run updatedb" "*/15 * * * * updatedb -l 0 -o ~/.config/mlocate/mlocate.db >> ~/.local/log/updatedb.log 2>&1")))

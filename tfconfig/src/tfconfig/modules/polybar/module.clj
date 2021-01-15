(ns tfconfig.modules.polybar.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn link-files
  [context]
  (let [files-dir (str (:modules-dir context) "polybar/files")
        config-src (str files-dir "/config")
        config-dest (str (:home context) ".config/polybar/config")
        startup-script-src (str files-dir "/polybar.sh")
        startup-script-dest (str (:home context) ".local/bin/polybar.sh")]
    (link context config-src config-dest)
    (command "ls" ["-lah" startup-script-src] (assoc context :sudo true))
    (link context startup-script-src startup-script-dest {:executable true})))

(defn run
  [context]
  (do
    (println "-- Module: polybar --")
    (file (str (:home context) ".config/polybar") (assoc context :state "dir" :owner (str (:username context) ":")))
    (command "paru" ["-S" "--noconfirm" "--needed" "polybar"] context)
    (command "ls" ["-lah" (str (:home context) ".config/polybar")] (assoc context :sudo true))
    (command "ls" ["-lah" (str (:home context) ".local")] (assoc context :sudo true))
    (link-files context)))

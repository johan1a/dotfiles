(ns tfconfig.modules.polybar.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn link-files
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)polybar/files")
        config-src (<< "~{files-dir}/config")
        config-dest (<< "~{home}.config/polybar/config")
        startup-script-src (<< "~{files-dir}/polybar.sh")
        startup-script-dest (<< "~{home}.local/bin/polybar.sh")]
    (link context config-src config-dest)
    (command "ls" ["-lah" files-dir] (assoc context :sudo true))
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

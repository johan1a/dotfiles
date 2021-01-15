(ns tfconfig.modules.polybar.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.handler :refer :all]))

(defn link-files
  [context]
  (let [files-dir (str (:modules-dir context) "polybar/files")
        config-src (str files-dir "/config")
        config-dest (str (:home context) ".config/polybar/config")
        startup-script-src (str files-dir "/polybar.sh")
        startup-script-dest (str (:home context) ".local/bin/polybar.sh")]
    (file config-dest (assoc context :state "link" :src config-src :owner (str (:username context) ":") ))
    (file startup-script-dest (assoc context :state "link" :src startup-script-src :executable "true"))))

(defn run
  [context]
  (do
    (println "-- Module: polybar --")
    (file (str (:home context) ".config/polybar") (assoc context :state "dir"))
    (command "paru" ["-S" "--noconfirm" "--needed" "polybar"] context)
    (link-files context)))

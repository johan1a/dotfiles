(ns tfconfig.modules.polybar.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.aur :refer [install-aur-package]]
   [tfconfig.common.file :refer [file link]]))

(defn link-files
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)polybar/files")
        config-src (<< "~{files-dir}/config")
        config-dest (<< "~{home}.config/polybar/config")
        startup-script-src (<< "~{files-dir}/polybar.sh")
        startup-script-dest (<< "~{home}.local/bin/polybar.sh")]
    (link context config-src config-dest)
    (link context startup-script-src startup-script-dest {:executable true})))

(defn run
  [context]
  (file (str (:home context) ".config/polybar") (assoc context :state "dir" :owner (str (:username context) ":")))
  (install-aur-package (assoc context :throw-errors false) "polybar")
  (link-files context))

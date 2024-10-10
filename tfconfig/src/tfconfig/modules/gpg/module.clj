(ns tfconfig.modules.gpg.module
  (:require
   [tfconfig.common.command :refer [command]])
  (:require [tfconfig.common.file :refer [file link]])
  (:require [tfconfig.common.pacman :refer [pacman]])
  (:require [tfconfig.common.handler :refer [handler]]))

; when not ci
(defn reload-gpg-agent
  [context _]
  (when-not (:ci context)
    (command "gpg-connect-agent" ["reloadagent" "/bye"] context)))

(defn run
  [context]
  (handler context :create-gpg-dir reload-gpg-agent)
  (let [base-dir (str (:home context) ".gnupg/")
        src-file (str (:modules-dir context) "gpg/files/gpg-agent.conf")
        dest-file (str base-dir "gpg-agent.conf")]
    (pacman "gnupg" context :present)
    (file base-dir (assoc context :state "dir" :handler-ref :create-gpg-dir))
    (link context src-file dest-file)))

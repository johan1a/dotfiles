(ns tfconfig.modules.gpg.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.handler :refer :all]))

; when not ci
(defn reload-gpg-agent
  [context changes]
  (when-not (:ci context)
    (command "gpg-connect-agent" ["reloadagent" "/bye"] context)))

(defn run
  [context]
  (do
    (handler context :create-gpg-dir reload-gpg-agent)
    (let [base-dir (str (:home context) ".gnupg/")
          src-file (str (:modules-dir context) "gpg/files/gpg-agent.conf")
          dest-file (str base-dir "gpg-agent.conf")]
      (pacman "gnupg" (assoc context :state "present"))
      (file base-dir (assoc context :state "dir" :handler-ref :create-gpg-dir))
      (link context src-file dest-file))))

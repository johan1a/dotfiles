(ns tfconfig.modules.gpg.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

; when not ci
(defn reload-gpg-agent
  [context key watched old-state new-state]
  (when (= key :create-gpg-dir)
    (command "gpg-connect-agent" ["reloadagent" "/bye"] context)))

(defn run
  [context]
  (do
    (println "Installing gpg")
    (add-watch (:changes context) :create-gpg-dir (partial reload-gpg-agent context))
    (let [base-dir (str (:home context) "/.gnupg-test/")
          src-file (str (:modules-dir context) "gpg/files/gpg-agent.conf")
          dest-file (str base-dir "gpg-agent.conf")]
      (pacman "gnupg" (assoc context :state "present"))
      (file base-dir (assoc context :state "dir" :change-ref :create-gpg-dir))
      (file dest-file (assoc context :state "link" :src src-file)))))

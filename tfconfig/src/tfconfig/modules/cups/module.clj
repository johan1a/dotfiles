(ns tfconfig.modules.cups.module
  (:require
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (let [sudo-context (assoc context :sudo true)]
    (pacman "cups" (assoc sudo-context :state "present"))
    (pacman "cups-pdf" (assoc sudo-context :state "present"))
    (command "usermod" ["-a" "-G" "sys" (:username context)] sudo-context)
    (when-not (:ci context)
      (command "systemctl" ["enable" "cups.service"] sudo-context)
      (command "systemctl" ["start" "cups.service"] sudo-context))))

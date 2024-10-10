(ns tfconfig.modules.docker.module
  (:require
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (pacman "docker" context :present)
  (pacman "docker-compose" context :present)
  (when-not (:ci context)
    (command "systemctl" ["enable" "docker"] context :sudo)
    (command "systemctl" ["start" "docker"] context :sudo))
  (command "usermod" ["-a" "-G" "docker" (:username context)] context :sudo))

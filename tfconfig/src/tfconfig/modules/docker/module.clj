(ns tfconfig.modules.docker.module
  (:require 
    [tfconfig.common.file :refer :all]
    [tfconfig.common.command :refer :all]
    [tfconfig.common.pacman :refer :all]
    [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (println "-- Module: docker --")
    (let [sudo-context (assoc context :sudo true)]
      (pacman "docker" (assoc sudo-context :state "present"))
      (pacman "docker-compose" (assoc sudo-context :state "present"))
      (when-not (:ci context)
        (command "systemctl" ["enable" "docker"] sudo-context)
        (command "systemctl" ["start" "docker"] sudo-context))
      (command "usermod" ["-a" "-G" "docker" (:username context)] sudo-context))))

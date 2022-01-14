(ns tfconfig.common.pacman
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.handler :refer [notify]]
            [clojure.string :as string]))

(defn pacman-upgrade
  [options]
  (command "pacman" ["-Syu"] (assoc options :sudo true)))

(defn pacman
  [package options]
  (let [desired-state (:state options)
        sudo-options (assoc options :sudo true)]
    (when (= desired-state "present")
      (println (str "Installing " package))
      (let [result (command "pacman" ["-S" package "--noconfirm" "--needed"] sudo-options)
            output (:stdout result)
            err (:stdout result)]
        (when-not (or (string/includes? output "is up to date -- skipping") (string/includes? err "is up to date -- skipping"))
          (notify options "installed"))))
    (when (= desired-state "absent")
      (command "pacman" ["-R" package "--noconfirm"] sudo-options))))

(ns tfconfig.common.pacman
  (:require [tfconfig.common.command :refer :all]))

(defn pacman-upgrade
  [options]
  (command "pacman" ["-Syu"] (assoc options :sudo true)))

(defn pacman
  [package options]
  (let [desired-state (:state options)
        sudo-options (assoc options :sudo true)]
    (do
      (when (= desired-state "present")
        (command "pacman" ["-S" package "--noconfirm" "--needed"] sudo-options)))
    (when (= desired-state "absent")
      (command "pacman" ["-R" package "--noconfirm"] sudo-options))))

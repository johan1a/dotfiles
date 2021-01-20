(ns tfconfig.common.pacman
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.handler :refer :all]))

(defn pacman-upgrade
  [options]
  (command "pacman" ["-Syu"] (assoc options :sudo true)))

(defn pacman
  [package options]
  (let [desired-state (:state options)
        sudo-options (assoc options :sudo true)]
    (do
      (when (= desired-state "present")
        (let [result (command "pacman" ["-S" package "--noconfirm" "--needed"] sudo-options)
              output (:stdout result)
              err (:stdout result)]
          (when-not (or (clojure.string/includes? output "is up to date -- skipping") (clojure.string/includes? err "is up to date -- skipping"))
            (notify options "installed"))))
      (when (= desired-state "absent")
        (command "pacman" ["-R" package "--noconfirm"] sudo-options)))))

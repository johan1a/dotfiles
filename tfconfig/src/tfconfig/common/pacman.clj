(ns tfconfig.common.pacman
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.handler :refer [notify]]
            [clojure.string :as string]))

(defn pacman-upgrade
  [options]
  (command "pacman" ["-Syu"] options :sudo))

(defn pacman
  [package context & args]
  (let [desired-state (if (some #(= % :absent) args) :absent :present)]
    (when (= desired-state :present)
      (println (str "Installing " package))
      (let [result (command "pacman" ["-S" package "--noconfirm" "--needed"] context :sudo)
            output (:stdout result)
            err (:stdout result)]
        (when-not (or (string/includes? output "is up to date -- skipping") (string/includes? err "is up to date -- skipping"))
          (notify context "installed"))))
    (when (= desired-state :absent)
      (command "pacman" ["-R" package "--noconfirm"] context :sudo))))

(ns tfconfig.common.pacman
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.handler :refer [notify]]
            [clojure.string :as string]))

(defn pacman-upgrade
  [options]
  (command "pacman" ["-Syu"] options :sudo))

(defn pacman
  [packages context & args]
  (let [desired-state (if (some #(= % :absent) args) :absent :present)
        packages (if (seqable? packages) packages [packages])]
    (when (= desired-state :present)
      (let [result (command "pacman" ["-S" packages "--noconfirm" "--needed"] context :sudo)
            output (:stdout result)
            err (:stdout result)]
        (when-not (or (string/includes? output "is up to date -- skipping") (string/includes? err "is up to date -- skipping"))
          (notify context "installed"))))
    (when (= desired-state :absent)
      (command "pacman" ["-R" packages "--noconfirm"] context :sudo))))

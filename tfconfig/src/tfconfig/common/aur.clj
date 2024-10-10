(ns tfconfig.common.aur
  (:require [tfconfig.common.command :refer [command pre-auth]]))

(defn install-aur-package
  [context package]
  (println (str "Installing " package))
  (command "paru" ["-S" "--noconfirm" "--needed" "--sudoloop" package] (assoc context pre-auth true) :sudo))

(defn install-aur-packages
  [context packages]
  (dorun (map #(install-aur-package context %) packages)))

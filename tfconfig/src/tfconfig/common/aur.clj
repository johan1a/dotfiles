(ns tfconfig.common.aur
  (:require [tfconfig.common.command :refer :all]))

(defn install-aur-package
  [context package]
  (println (str "Installing " package))
  (command "paru" ["-S" "--noconfirm" "--needed" "--sudoloop" package] (assoc context pre-auth true)))

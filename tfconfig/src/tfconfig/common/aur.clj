(ns tfconfig.common.aur
  (:require [tfconfig.common.command :refer :all]))

(defn paru
  [context package]
  (command "paru" ["-S" "--noconfirm" "--needed" "--sudoloop" package] (assoc context pre-auth true)))

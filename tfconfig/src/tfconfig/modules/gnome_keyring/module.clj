(ns tfconfig.modules.gnome-keyring.module
  (:require [tfconfig.common.file :refer [link]])
  (:require [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
  (let [src-file (str (:modules-dir context) "gnome_keyring/files/login")
        dest-file "/etc/pam.d/login"]
    (pacman "gnome-keyring" context :present)
    (link context src-file dest-file {:sudo true})))

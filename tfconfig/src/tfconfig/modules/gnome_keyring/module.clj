(ns tfconfig.modules.gnome-keyring.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  )

(defn run
  [context]
  (do
    (let [src-file (str (:modules-dir context) "gnome_keyring/files/login")
          dest-file "/etc/pam.d/login"]
      (pacman "gnome-keyring" (assoc context :state "present"))
      (link (assoc context :sudo true) src-file dest-file))))

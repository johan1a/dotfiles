(ns tfconfig.common.aur
  (:require [tfconfig.common.command :refer [command]]))

(defn aur-packages
  [context packages]
  (let [packages (if (seqable? packages) packages [packages])]
    (command "yay" ["-S" "--noconfirm" "--needed" "--sudoloop" packages] (assoc context :pre-auth true))))

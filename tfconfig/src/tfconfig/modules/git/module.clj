(ns tfconfig.modules.git.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [link]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn run
  [context]
    ; We most likely have git installed already, but keeping this for good measure.
  (pacman "git" (assoc context :state "present"))
  (let [files-dir (<< "~(:modules-dir context)git/files")
        home (:home context)]
    (link context (str files-dir "/gitconfig") (str home ".gitconfig"))
    (link context (str files-dir "/gitignore_global") (str home ".gitignore_global"))))

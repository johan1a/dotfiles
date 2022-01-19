(ns tfconfig.modules.git.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [link]]))

(defn run
  [context]
  (let [files-dir (<< "~(:modules-dir context)git/files")
        home (:home context)]
    (link context (str files-dir "/gitconfig") (str home ".gitconfig"))
    (link context (str files-dir "/gitignore_global") (str home ".gitignore_global"))))

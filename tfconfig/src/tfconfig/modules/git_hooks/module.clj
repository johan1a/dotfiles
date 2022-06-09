(ns tfconfig.modules.git-hooks.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [link, file]]))

(defn run
  [context]
  (let [files-dir (<< "~(:modules-dir context)git_hooks/files")
        src-file (str files-dir "/commit-msg")
        dest-dir (str (:root-dir context) "/.git/hooks")
        dest-file (str dest-dir "/commit-msg")]
    (file dest-dir (assoc context :state "dir"))
    (link context src-file dest-file)))

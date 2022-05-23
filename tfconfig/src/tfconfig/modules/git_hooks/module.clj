(ns tfconfig.modules.git-hooks.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.file :refer [link]]))

(defn run
  [context]
  (let [files-dir (<< "~(:modules-dir context)git_hooks/files")
        src-file (str files-dir "/commit-msg")
        dest-file (str (:root-dir context) "/.git/hooks/commit-msg")]
    (link context src-file dest-file)))

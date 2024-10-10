(ns tfconfig.modules.dirs.module
  (:require
   [tfconfig.common.file :refer [directory]]))

(defn run
  "Create standard directories"
  [context]
  (let [home (:home context)
        owner (str (:username context) ":")
        dirs [(:sources-dir context)
              (:backup-dir context)
              (str home "dev") ; My code
              (str home ".dotfiles_backup")
              (str home "source") ; Third-party code, for compilation
              (str home "programs") ; For portable programs e.g. Intellij
              (str home ".local")
              (str home ".local/bin")
              (str home ".local/lib")
              (str home ".local/log")
              (str home ".config")]]
    (dorun (map #(directory context % {:owner owner}) dirs))))

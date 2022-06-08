(ns tfconfig.modules.utils.module
  (:require [tfconfig.common.file :refer [link directory]]
            [clojure.core.strint :refer [<<]]
            [clojure.java.io :refer [file]]))

(defn run
  "Installs useful utility scripts"
  [context]
  (let [executable-context (assoc context :executable true)
        home (:home context)
        bin-dir (<< "~(:modules-dir context)utils/files/bin")]
    (doseq [script (.list (file bin-dir))]
      (when script
        (link executable-context
              (<< "~{bin-dir}/~{script}")
              (str home ".local/bin/" script))))
    (link executable-context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/s")
          (str home ".local/bin/s"))
    (link executable-context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/new-local-script")
          (str home ".local/bin/new-local-script"))
    (link executable-context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/edit-local-script")
          (str home ".local/bin/edit-local-script"))
    (link executable-context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/list-local-scripts")
          (str home ".local/bin/list-local-scripts"))
    (directory context (str home ".config/fish/completions"))
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/s_completions.fish")
          (str home ".config/fish/completions/s.fish"))))

(ns tfconfig.modules.utils.module
  (:require [tfconfig.common.file :refer [link directory]]
            [clojure.core.strint :refer [<<]]
            [clojure.java.io :refer [file]]))

(defn run
  "Installs useful utility scripts"
  [context]
  (let [home (:home context)
        bin-dir (<< "~(:modules-dir context)utils/files/bin")]
    (doseq [script (.list (file bin-dir))]
      (when script
        (link context
              (<< "~{bin-dir}/~{script}")
              (str home ".local/bin/" script)
              {:executable true})))
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/s")
          (str home ".local/bin/s")
          {:executable true})
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/new-local-script")
          (str home ".local/bin/new-local-script")
          {:executable true})
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/edit-local-script")
          (str home ".local/bin/edit-local-script")
          {:executable true})
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/list-local-scripts")
          (str home ".local/bin/list-local-scripts")
          {:executable true})
    (directory context (str home ".config/fish/completions"))
    (link context
          (<< "~(:modules-dir context)utils/files/local_scripts_common/s_completions.fish")
          (str home ".config/fish/completions/s.fish"))))

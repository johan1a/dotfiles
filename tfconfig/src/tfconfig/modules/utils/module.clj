(ns tfconfig.modules.packages.module
  (:require [tfconfig.common.file :refer [link directory]]
            [clojure.core.strint :refer [<<]]))

(defn run
  "Installs useful utility scripts"
  [context]
  (let [executable-context (assoc context :executable true)
        home (:home context)]
    (link executable-context
          (<< "~(:modules-dir context)packages/files/bin/background-notify")
          (str home ".local/bin/background-notify"))
    (link executable-context
          (<< "~(:modules-dir context)packages/files/bin/stomp_start_jack")
          (str home ".local/bin/stomp_start_jack"))
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

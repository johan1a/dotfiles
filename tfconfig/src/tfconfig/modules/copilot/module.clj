(ns tfconfig.modules.copilot.module
  (:require [tfconfig.common.file :refer [link directory]]))

(defn run
  [context]
  (let [files-dir (str (:modules-dir context) "copilot/files/")
        intellij-dir (str (:home context) ".config/github-copilot/intellij")]
    (directory context (str (:home context) ".copilot"))
    (directory context intellij-dir)
    (link context (str files-dir "global-copilot-instructions.md") (str (:home context) ".copilot/copilot-instructions.md"))
    (link context (str files-dir "global-copilot-instructions.md") (str intellij-dir "/global-copilot-instructions.md"))))

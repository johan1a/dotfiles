(ns tfconfig.modules.intellij.module
  (:require [tfconfig.common.file :refer [link]]
            [tfconfig.common.aur :refer [aur-packages]]
            [tfconfig.common.file :refer [directory]]))

(defn run
  [context]
  (let [intellij-dir (str (:home context) ".config/github-copilot/intellij")]
    (link context (str (:modules-dir context) "intellij/files/ideavimrc") (str (:home context) ".ideavimrc"))
    (directory context intellij-dir)
    (link context (str (:modules-dir context) "intellij/files/global-copilot-instructions.md") (str intellij-dir "/global-copilot-instructions.md"))
    (when (= (:os context) "archlinux")
      (aur-packages context "jetbrains-toolbox"))))

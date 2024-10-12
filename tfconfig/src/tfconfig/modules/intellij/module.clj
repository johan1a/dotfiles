(ns tfconfig.modules.intellij.module
  (:require [tfconfig.common.file :refer [link]]
            [tfconfig.common.aur :refer [aur-packages]]))

(defn run
  [context]
  (link context (str (:modules-dir context) "intellij/files/ideavimrc") (str (:home context) ".ideavimrc"))
  (aur-packages context "jetbrains-toolbox"))

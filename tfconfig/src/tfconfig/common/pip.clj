(ns tfconfig.common.pip
  (:require
   [clojure.string :as string]
   [tfconfig.common.apt :as apt]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.has-executable :refer [has-executable?]]
   [tfconfig.common.pacman :refer [pacman]]))

(defn pip
  [context package-name desired-state]
  (let [os (:os context)]
    (when-not (has-executable? context "pip")
      (when (= os "archlinux")
        (pacman "python-pip" context))
      (when (= os "raspbian")
        (apt/install context ["python-pip"])))
    (let [disabled-errors-context (assoc context :throw-errors false)
          cmd-result (command "pip" ["list" package-name] disabled-errors-context)
          is-present (string/includes? (:stdout cmd-result) package-name)]
      (when (= desired-state "present")
        (when-not is-present
          (command "pip" ["install" "--user" package-name] context))))))

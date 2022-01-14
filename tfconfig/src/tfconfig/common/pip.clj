(ns tfconfig.common.pip
  (:require [tfconfig.common.command :refer [command]]
            [clojure.string :as string])
  (:require [tfconfig.common.pacman :refer [pacman]])
  (:require [tfconfig.common.has-executable :refer [has-executable?]]))

(defn pip
  [context package-name desired-state]
  (when-not (has-executable? context "pip")
    (pacman "python-pip" (assoc context :state "present")))
  (let [disabled-errors-context (assoc context :throw-errors false)
        cmd-result (command "pip" ["list" package-name] disabled-errors-context)
        is-present (string/includes? (:stdout cmd-result) package-name)]
    (when (= desired-state "present")
      (when-not is-present
        (command "pip" ["install" "--user" package-name] context)))))

(ns tfconfig.common.pip
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(defn pip
  [context package-name desired-state]
  (do
    (when-not (has-executable? context "pip")
      (pacman "python-pip" (assoc context :state "present")))
    (let [disabled-errors-context (assoc context :throw-errors false)
          cmd-result (command "pip" ["list" package-name] disabled-errors-context)
          is-present (clojure.string/includes? (:stdout cmd-result) package-name)]
    (when (= desired-state "present")
      (when-not is-present
        (command "pip" ["install" "--user" package-name] context))))))

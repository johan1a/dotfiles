(ns tfconfig.common.pip
  (:require [tfconfig.common.command :refer :all]))

(defn pip
  [context package-name desired-state]
  (let [disabled-errors-context (assoc context :throw-errors false)
        cmd-result (command "gem" ["list" package-name] disabled-errors-context)
        is-present (clojure.string/includes? (:stdout cmd-result) package-name)]

  (when (= desired-state "present")
    (when-not is-present
      (command "gem" ["install" package-name] context)))))

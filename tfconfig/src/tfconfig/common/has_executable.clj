(ns tfconfig.common.has-executable
  (:require [tfconfig.common.command :refer [command]]))

(defn has-executable?
  [context executable]
  (let [disabled-errors-context (assoc context :throw-errors false)
        result (command "which" [executable] disabled-errors-context)
        exit-code (:code result)]
    (= exit-code 0)))

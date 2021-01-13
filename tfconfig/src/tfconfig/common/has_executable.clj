(ns tfconfig.common.has-executable
  (:require [tfconfig.common.command :refer :all]))

(defn has-executable?
  [executable]
  (let [result (command "which" [executable] {})
        exit-code (:code result)]
    (= exit-code 0)))

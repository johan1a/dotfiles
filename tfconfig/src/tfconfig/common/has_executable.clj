(ns tfconfig.common.has-executable
  (:require [tfconfig.common.command :refer :all]))

(defn has-executable?
  [executable]
  (println (str "has-executable? " executable))
  (let [result (command "which" [executable] {})
        exit-code (:exit result)]
    (println result)
    (= exit-code 0)))

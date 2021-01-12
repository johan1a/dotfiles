(ns tfconfig.common.has-executable
  (:require [clojure.java.shell :as shell]))

(defn has-executable?
  [executable]
  (let [result (shell/sh "which" executable)
        exit-code (:exit result)]
    (= exit-code 0)))

(ns tfconfig.common.command
  (:require [clojure.java.shell :as shell]))

(defn command
  [& args]
  (println (apply shell/sh args)))

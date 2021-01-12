(ns tfconfig.core
  (:require [tfconfig.modules.aur :as aur])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (aur/run)
  (shutdown-agents)
  )

(ns tfconfig.core
  (:require [tfconfig.modules.aur :as aur])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [context {
                 :root-dir "/home/johan/dotfiles/"
                 :sources-dir "/home/johan/source/"
                 }]
    (println "Configuring...")
    (aur/run context)
    (println "Done!")
    (shutdown-agents)))

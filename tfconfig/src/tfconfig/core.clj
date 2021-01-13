(ns tfconfig.core
  (:require [tfconfig.modules.aur :as aur])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [password (first args)
        context {
                 :root-dir "/home/johan/dotfiles/"
                 :sources-dir "/home/johan/source/"
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 }]
    (if password
      (do
        (println "Configuring...")
        (aur/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password supplied"))))

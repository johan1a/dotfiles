(ns tfconfig.core
  (:require [tfconfig.modules.aur :as aur])
  (:gen-class))


(defn get-password
  [args]
  (if args
    (let [i (.indexOf args "--password")]
      (if-not (= i -1)
        (nth args (+ i 1))))))

(defn -main
  [& args]
  (let [password (get-password args)
        context {
                 :root-dir "/home/johan/dotfiles/"
                 :sources-dir "/home/johan/source/"
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username "johan"
                 }]
    (if password
      (do
        (println "Configuring...")
        (aur/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

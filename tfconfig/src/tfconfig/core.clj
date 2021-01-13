(ns tfconfig.core
  (:require [tfconfig.modules.aur :as aur])
  (:gen-class))


(defn get-arg-value
  [args argname]
  (if args
    (let [i (.indexOf args argname)]
      (if-not (= i -1)
        (nth args (+ i 1))))))

(defn get-password
  [args]
  (get-arg-value args "--password"))

(defn get-user
  [args]
  (get-arg-value args "--user"))

(defn -main
  [& args]
  (let [password (get-password args)
        user (get-user args)
        context {
                 :root-dir "/home/johan/dotfiles/"
                 :sources-dir "/home/johan/source2/"
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 }]
    (if password
      (do
        (println "Configuring...")
        (aur/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

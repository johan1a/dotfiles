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
  (let [password (get-arg-value args "--password")]
    (if password
        password
        (System/getenv "PASSWORD"))))

(defn get-user
  [args]
  (let [user (get-arg-value args "--user")]
  (if user
      user
      (System/getenv "USER"))))

(defn -main
  [& args]
  (let [password (get-password args)
        user (get-user args)
        context {
                 :root-dir "/home/johan/dotfiles/"
                 :sources-dir "/home/johan/source/"
                 :password password
                 :verbose (some #(= "--verbose" %) args)
                 :username user
                 }]
    (if password
      (do
        (aur/run context)
        (println "Done!")
        (shutdown-agents))
      (println "No password specified"))))

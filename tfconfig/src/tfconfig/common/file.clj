(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer :all]))

(defn file
  [dir options]
  (println "file")
  (let [desired-state (:state options)
        owner (:owner options)
        is-dir (= 0 (:code (command "test" ["-d" dir] options)))
        is-file (= 0 (:code (command "test" ["-f" dir] options)))
        ]
    (println "is-dir:" is-dir)
    (println "is-file" is-dir)
    (do
      (when (and (= desired-state "dir") (not is-dir))
          (println (str "Creating directory: " dir)
          (command "mkdir" ["-p" dir] (assoc options :sudo true))))
      (when owner
        (println (str "Setting owner to: " owner " for directory: " dir)
        (command "chown" [owner dir] (assoc options :sudo true)))))))

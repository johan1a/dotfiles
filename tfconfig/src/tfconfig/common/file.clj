(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer :all]))

(defn file
  [dir options]
  (let [desired-state (:state options)
        owner (:owner options)
        is-dir (= 0 (:code (command "stat" ["-d" dir] options)))
        is-file (= 0 (:code (command "stat" ["-f" dir] options)))
        ]
    (when (and (= desired-state "dir") (not is-dir))
        (println (str "Creating directory: " dir)
        (command "mkdir" ["-p" dir] {:sudo true :verbose (:verbose options)})))
    (when owner
      (println (str "Setting owner to: " owner " for directory: " dir)
      (command "chown" [owner dir] {:sudo true :verbose (:verbose options)})))))

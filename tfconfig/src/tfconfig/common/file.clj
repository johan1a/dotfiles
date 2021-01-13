(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer :all]))

(defn file
  [dir options]
  (let [desired-state (:state options)
        is-dir (= 0 (:code (command "stat" ["-d" dir] {})))
        is-file (= 0 (:code (command "stat" ["-f" dir] {})))]
    (when (and (= desired-state "dir") (not is-dir))
      (command "mkdir" ["-p" dir] {}))))

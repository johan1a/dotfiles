(ns tfconfig.common.file
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.handler :refer :all]))

(defn file
  [path context]
  (let [desired-state (:state context)
        owner (:owner context)
        src (:src context)
        disabled-errors-context (assoc context :throw-errors false)
        is-dir (= 0 (:code (command "test" ["-d" path] disabled-errors-context)))
        is-file (= 0 (:code (command "test" ["-f" path] disabled-errors-context)))
        is-link (= 0 (:code (command "test" ["-L" path] disabled-errors-context)))]
    (do
      (when (and (= desired-state "dir") (not is-dir))
        (do
          (println (str "Creating directory: " path))
          (command "mkdir" ["-p" path] (assoc context :sudo true))
          (command "chown" [(:username context) path] (assoc context :sudo true))
          (notify context "created")))
      (when (= desired-state "link")
        (do
        (when is-link
          (command "rm" [path] context))
        (when (or is-dir is-file)
          (command "mv" [path (:backup-dir context)] context))
        (command "ln" ["-s" src path] context)
        (when (:executable context)
          (command "chmod" ["+x" path] (assoc context :sudo true)))))
      (when owner
        (command "chown" [owner path] (assoc context :sudo true))))))

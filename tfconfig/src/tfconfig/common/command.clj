(ns tfconfig.common.command
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [me.raynes.conch :refer [programs with-programs let-programs] :as conch])
  (:use [clojure.java.io :only (as-file)])
  (:import (java.io InputStreamReader OutputStreamWriter)))


(defn log
  [options & args]
  (when (:verbose options) (println args)))


; from https://stackoverflow.com/a/45293277
(defn run-proc
  [options proc-name args stdout-callback stderr-callback]
  (let [sudo-prompt (:sudo-prompt options)
        dir (:dir options)
        input (:input options)
        pbuilder (ProcessBuilder. (into-array String (flatten [proc-name args])))
        env (.environment pbuilder)
        _0 (when sudo-prompt (.put env "SUDO_PROMPT" sudo-prompt))
        _1 (when dir (.directory pbuilder (clojure.java.io/file dir)))
        process (.start pbuilder)]
    (with-open [stdout (clojure.java.io/reader (.getInputStream process))]
      (with-open [stderr (clojure.java.io/reader (.getErrorStream process))]
        (with-open [stdin (clojure.java.io/writer (.getOutputStream process))]
          (when input
            (do
              (.write stdin (str input "\n"))
              (.flush stdin)))
            (let [
                  stderr-future (future (stderr-callback stderr stdin))
                  stdout-future (future (stdout-callback stdout stdin))
                  stderr-result (deref stderr-future)
                  stdout-result (deref stdout-future)
                  ]))))))

(defn out-callback
  [context stdout stdin]
    (loop []
      (when-let [line (.readLine ^java.io.BufferedReader stdout)]
        (log context (str "stdin: " line) line)
        (recur))))

(defn err-callback
  [context sudo-prompt password stderr stdin]
    (loop []
      (when-let [line (.readLine ^java.io.BufferedReader stderr)]
        (log context (str "stderr: " line))
          ; (println (str "stderr: " line))
           ; (when (clojure.string/includes? line sudo-prompt)
           ;  (println "writing" )
           ;    (.write stdin (str password "\n"))
           ;    (.flush stdin))
         (recur))))

(defn pre-auth
  [options]
  (let [new-options (assoc options :input (:password options))
        stderr-callback (partial err-callback options (:sudo-prompt options) (:password options))]
  (run-proc new-options "sudo" ["-S" "true"] (partial out-callback options) stderr-callback)))

(defn run-with-password
  [cmd args options]
  (log cmd args)
  (let [sudo-prompt "thesudoprompt"
        password (:password options)
        new-options (assoc options :sudo-prompt sudo-prompt)]
    (if (:pre-auth options)
      (pre-auth new-options))
    (run-proc new-options cmd args (partial out-callback options) (partial err-callback options sudo-prompt password))))

(defn command
  [command args options]
  (run-with-password command args options))
(ns tfconfig.common.command
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [me.raynes.conch :refer [programs with-programs let-programs] :as conch])
  (:use [clojure.java.io :only (as-file)])
  (:import (java.io InputStreamReader OutputStreamWriter)))

; from https://stackoverflow.com/a/45293277
(defn run-proc
  [options proc-name args stdout-callback stderr-callback]
      (println proc-name args options)
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
              (println (str "writing to stdin: " input))
              (.write stdin (str input "\n"))
              (.flush stdin)))
            (let [
                  stderr-future (future (stderr-callback stderr stdin))
                  stdout-future (future (stdout-callback stdout stdin))
                  stderr-result (deref stderr-future)
                  stdout-result (deref stdout-future)
                  ]))))))

(defn out-callback
  [stdout stdin]
    (loop []
      (when-let [line (.readLine ^java.io.BufferedReader stdout)]
        (println (str "stdout: " line))
        (recur))))

(defn err-callback
  [sudo-prompt password stderr stdin]
    (loop []
      (when-let [line (.readLine ^java.io.BufferedReader stderr)]
          (println (str "stderr: " line))
           ; (when (clojure.string/includes? line sudo-prompt)
           ;  (println "writing" )
           ;    (.write stdin (str password "\n"))
           ;    (.flush stdin))
         (recur))))

(defn pre-auth
  [options]
  (println "pre-auth")
  (let [new-options (assoc options :input (:password options))
        stderr-callback (partial err-callback (:sudo-prompt options) (:password options))]
  (run-proc new-options "sudo" ["-S" "ls" "-lah"] out-callback stderr-callback)))

(defn run-with-password
  [options cmd args]
  (println cmd args)
  (let [sudo-prompt "thesudoprompt"
        password (:password options)
        new-options (assoc options :sudo-prompt sudo-prompt)]
    (if (:pre-auth options)
      (pre-auth new-options))
    (run-proc new-options cmd args out-callback (partial err-callback sudo-prompt password))))

(defn command
  [command args options]
  (run-with-password options command args))

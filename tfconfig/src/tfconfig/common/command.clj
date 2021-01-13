(ns tfconfig.common.command
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [me.raynes.conch :refer [programs with-programs let-programs] :as conch])
  (:use [clojure.java.io :only (as-file)])
  (:import (java.io InputStreamReader OutputStreamWriter)))

; from https://stackoverflow.com/a/45293277
(defn run-proc
  [sudo-prompt dir proc-name args stdout-callback stderr-callback]
  (let [pbuilder (ProcessBuilder. (into-array String (flatten [proc-name args])))
        env (.environment pbuilder)
        _0 (.put env "SUDO_PROMPT" sudo-prompt)
        _1 (when dir (.directory pbuilder (clojure.java.io/file dir)))
        process (.start pbuilder)]
    (with-open [stdout (clojure.java.io/reader (.getInputStream process))]
      (with-open [stderr (clojure.java.io/reader (.getErrorStream process))]
        (with-open [stdin (clojure.java.io/writer (.getOutputStream process))]
            (let [stdout-future (future (stdout-callback stdout stdin))
                  stderr-future (future (stderr-callback stderr stdin))
                  stdout-result (deref stdout-future)
                  stderr-result (deref stderr-future)]))))))

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
           (when (clojure.string/includes? line sudo-prompt)
            (println "writing" )
              (.write stdin (str password "\n"))
              (.flush stdin))
         (recur))))

(defn run-with-password
  [options cmd args]
  (println cmd args)
  (let [sudo-prompt "thesudoprompt"
        password (:password options)]
    (run-proc sudo-prompt (:dir options) cmd args out-callback (partial err-callback sudo-prompt password))))

(defn command
  [command args]
  (run-with-password {:password "test"} command args))

(defn command-in-dir
  [dir command args]
  (run-with-password {:password "test" :dir dir} command args))

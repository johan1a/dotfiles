(ns tfconfig.common.command
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]
            [me.raynes.conch :refer [programs with-programs let-programs] :as conch])
  (:use [clojure.java.io :only (as-file)])
  (:import (java.io InputStreamReader OutputStreamWriter)))

; from https://stackoverflow.com/a/45293277
(defn run-proc
  [proc-name arg-string callback]
  (let [pbuilder (ProcessBuilder. (into-array String [proc-name arg-string]))
        process (.start pbuilder)]
    (with-open [reader (clojure.java.io/reader (.getInputStream process))]
      (with-open [error (clojure.java.io/reader (.getErrorStream process))]
        (with-open [writer (clojure.java.io/writer (.getOutputStream process))]
          (loop []
            (let [line (.readLine ^java.io.BufferedReader reader)
                  err-line (.readLine ^java.io.BufferedReader reader)]
              (callback writer line err-line)
              (recur))))))))

(defn enter-password
  [password writer line err-line]
  (println "enter-password")
  (println line)
  (println err-line)
  (.write writer "user input"))

(defn run-with-password
  [options cmd args]
  (println "run-with-password")
  (let [password (:password options)]
    (run-proc cmd args (partial enter-password password))))

 ; [sudo] password for
(defn command
  [& args]
  (run-with-password {:password "test"} "sleep" "1; echo hey")
  (println "(apply shell/sh args)"))

(defn command-in-dir
  [dir & args]
  (println args)
  (shell/with-sh-dir dir
    (println "(apply shell/sh args)")))

; (with-open [rdr (clojure.java.io/reader (:out (sh/proc "watch" "ls")))] (printf "%s\n" (clojure.string/join "\n" (line-seq rdr))))

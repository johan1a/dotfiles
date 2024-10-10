(ns tfconfig.common.command
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def sudo-prompt "Enter SUDO password (WIP): ")

(defn log
  [options & args]
  (when (:verbose options) (println (str/join " " args))))

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
            (.write stdin (str input "\n"))
            (.flush stdin))
          (let [stderr-future (future (stderr-callback stderr stdin))
                stdout-future (future (stdout-callback stdout stdin))
                stderr-result (deref stderr-future)
                stdout-result (deref stdout-future)]
            (.waitFor process)
            {:code (.exitValue process) :stdout stdout-result :stderr stderr-result}))))))

(defn out-callback
  [context stdout stdin]
  (loop [lines []]
    (let [line (.readLine ^java.io.BufferedReader stdout)]
      (if-not line
        lines
        (do
          (log context (str "stdout: " line))
          (recur (conj lines line)))))))

(defn err-callback
  [context sudo-prompt password stderr stdin]
  (loop [lines []]
    (let [line (.readLine ^java.io.BufferedReader stderr)]
      (if-not line
        lines
        (do
          (log context (str "stderr: " line))
          (recur (conj lines line)))))))

(defn pre-auth
  [options password]
  (let [new-options (assoc options :input password)
        stderr-callback (partial err-callback options sudo-prompt password)]
    (run-proc new-options "sudo" ["-S" "true"] (partial out-callback options) stderr-callback)))

(defn command
  [cmd args context & rest]
  (log context cmd args)
  (let [sudo (some #(= % :sudo) rest)
        password (:password context)
        new-options (assoc context :input (if sudo password nil))
        new-cmd (if sudo "sudo" cmd)
        new-args (if sudo (concat ["-S" cmd] args) args)]
    (when (:pre-auth context)
      (pre-auth context password))
    (let [result (run-proc new-options new-cmd new-args (partial out-callback context) (partial err-callback context sudo-prompt password))]
      (if (or (= 0 (:code result)) (not (:throw-errors context)))
        result
        (throw (ex-info (str "Error: command failed: " cmd " " args) result))))))

(ns tfconfig.common.command
  (:require [clojure.java.io :as io]))

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
  [options]
  (let [new-options (assoc options :input (:password options))
        stderr-callback (partial err-callback options (:sudo-prompt options) (:password options))]
    (run-proc new-options "sudo" ["-S" "true"] (partial out-callback options) stderr-callback)))

(defn command
  [cmd args options]
  (log options cmd args)
  (let [sudo-prompt "thesudoprompt"
        sudo (:sudo options)
        password (:password options)
        new-options (assoc options :sudo-prompt sudo-prompt :input (if sudo password nil))
        new-cmd (if sudo "sudo" cmd)
        new-args (if sudo (concat ["-S" cmd] args) args)]
    (when (:pre-auth options)
      (pre-auth new-options))
    (let [result (run-proc new-options new-cmd new-args (partial out-callback options) (partial err-callback options sudo-prompt password))]
      (if (or (= 0 (:code result)) (not (:throw-errors options)))
        result
        (throw (ex-info (str "Error: command failed: " cmd " " args) result))))))

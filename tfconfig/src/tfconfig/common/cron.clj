(ns tfconfig.common.cron
  (:require
   [tfconfig.common.file :refer [directory file]]
   [clojure.core.strint :refer [<<]]
   [clojure.java.io :as io]))

(defn updated-content
  [lines index managed-str job]
  (if (= -1 index)
    (concat lines [managed-str job])
    (concat (take index lines) [managed-str job] (drop (+ 2 index) lines))))

(defn cron
  [context name job]
  (let [managed-str (str (:managed-str context) name)
        cron-file (<< "/var/spool/cron/~(:username context)")]
    (directory context "/var/spool/cron" {:sudo true})
    (file context cron-file {:sudo true :owner (str (:username context) ":")})
    (with-open [reader (io/reader cron-file)]
      (let [lines (line-seq reader)
            index (.indexOf (or lines []) managed-str)
            new-lines (updated-content lines index managed-str job)]
        (with-open [writer (io/writer cron-file)]
          (dorun (map #(.write writer (str % "\n")) new-lines)))))))

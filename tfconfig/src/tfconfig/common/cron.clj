(ns tfconfig.common.cron
  (:require [tfconfig.common.command :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn updated-content
  [lines index managed-str job]
        (if (= -1 index)
          (concat lines [managed-str job])
          (concat (take index lines) [managed-str job] (drop (+ 2 index) lines))))

(defn cron
  [context name job]
  (let [managed-str (str (:managed-str context) name)
        cron-file (<< "/var/spool/cron/~(:username context)")]
    (with-open [reader (clojure.java.io/reader cron-file)]
      (let [lines (line-seq reader)
            index (.indexOf lines managed-str)
            new-lines (updated-content lines index managed-str job)]
        (with-open [writer (clojure.java.io/writer cron-file)]
          (dorun (map #(.write writer (str % "\n")) new-lines)))))))

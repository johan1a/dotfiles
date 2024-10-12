(ns tfconfig.common.module
  (:require
   [clojure.string :as string]
   [clojure.java.io :as io]))

(defn- is-module
  [file]
  (clojure.string/includes? (.getAbsolutePath file) "module.clj"))

(defn- get-parent-name
  [file]
  (let [parent (.getParent file)]
    (last (clojure.string/split parent #"/"))))

(defn- discover-single-file-modules
  [modules-dir]
  (let [files (file-seq (io/file modules-dir))
        top-level-files (filter #(= modules-dir (str (.getParent %) "/")) files)
        module-files (filter #(.endsWith (.getName %) ".clj") top-level-files)]
    (map #(hash-map :file % :name (.replace (.getName %) ".clj" "")) module-files)))

(defn discover-modules
  [modules-dir]
  (let [module-files (filter is-module (file-seq (io/file modules-dir)))
        modules (map #(hash-map :name (get-parent-name %) :file %) module-files)
        single-file-modules (discover-single-file-modules modules-dir)]
    (concat modules single-file-modules)))

(defn run-module
  [module context]
  (let [module-name (:name module)
        run-module (load-file (.getAbsolutePath (:file module)))
        startTime (. System (currentTimeMillis))
        _ (run-module context)
        elapsedMillis (- (. System (currentTimeMillis)) startTime)
        elapsedSeconds (/ elapsedMillis 1000.0)]
    {:elapsed elapsedSeconds :module module-name}))


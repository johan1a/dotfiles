(ns tfconfig.modules.ctags.module
  (:require [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.command :refer :all]
            [tfconfig.common.aur :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn install-universal-ctags
  [context]
  (let [source-dir (str (:sources-dir context) "universal_ctags")]
    (when-not (dir-exists? context source-dir)
      (command "git" ["clone" "https://github.com/universal-ctags/ctags.git" source-dir] context)
      (command "./autogen.sh" [] (assoc context :dir source-dir))
      (command "./configure" [] (assoc context :dir source-dir))
      (command "make" [] (assoc context :dir source-dir))
      (command "make" ["install"] (assoc context :dir source-dir :sudo true)))))

(defn run
  [context]
  (do
    (println "-- Module: ctags --")
      (install-universal-ctags context)
      (let [config-dir (str (:home context) ".ctags.d")
            src-file (<< "~(:modules-dir context)ctags/files/main.ctags")
            dest-file (str config-dir "/main.ctags")]
            (directory context config-dir)
            (link context src-file dest-file))))

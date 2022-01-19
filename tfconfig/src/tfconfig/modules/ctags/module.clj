(ns tfconfig.modules.ctags.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.apt :as apt]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.file :refer [dir-exists? directory link]]))

(defn install-universal-ctags
  [context]
  (if (= (:os context) "raspbian")
    (apt/install (assoc context :sudo true) ["universal-ctags"])
    (let [source-dir (str (:sources-dir context) "universal_ctags")]
      (when-not (dir-exists? context source-dir)
        (command "git" ["clone" "https://github.com/universal-ctags/ctags.git" source-dir] context)
        (command "./autogen.sh" [] (assoc context :dir source-dir))
        (command "./configure" [] (assoc context :dir source-dir))
        (command "make" [] (assoc context :dir source-dir))
        (command "make" ["install"] (assoc context :dir source-dir :sudo true))))))

(defn run
  [context]
  (install-universal-ctags context)
  (let [config-dir (str (:home context) ".ctags.d")
        src-file (<< "~(:modules-dir context)ctags/files/main.ctags")
        dest-file (str config-dir "/main.ctags")]
    (directory context config-dir)
    (link context src-file dest-file)))

(ns tfconfig.modules.fzf.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.aur :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn run
  [context]
  (do
    (let [fzf-dir (<< "~(:home context).fzf")]
      (when-not (dir-exists? context fzf-dir)
        (command "git" ["clone" "--depth" "1" "https://github.com/junegunn/fzf.git" fzf-dir] context)
        (file (<< "~(:home context).config/fish/functions/fzf_key_bindings.fish") (assoc context :state "absent"))
        (command "./install" ["--all"] (assoc context :dir fzf-dir))))))

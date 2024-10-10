(ns tfconfig.common.gem
  (:require
   [clojure.string :as string]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.has-executable :refer [has-executable?]]
   [tfconfig.common.pacman :refer [pacman]]))

; TODO WARNING:  You don't have /home/johan/.gem/ruby/2.7.0/bin in your PATH,
;           gem executables will not run.


(defn gem
  [context gem-name desired-state]
  (when-not (has-executable? context "gem")
    (let [os (:os context)]
      (when (= os "archlinux")
        (pacman "rubygems" context :present))))
  (let [disabled-errors-context (assoc context :throw-errors false)
        cmd-result (command "gem" ["list" gem-name] disabled-errors-context)
        is-present (string/includes? (:stdout cmd-result) gem-name)]
    (when (= desired-state "present")
      (when-not is-present
        (command "gem" ["install" gem-name] context)))))

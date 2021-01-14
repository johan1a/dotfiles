(ns tfconfig.common.gem
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.pacman :refer :all]))


; TODO WARNING:  You don't have /home/johan/.gem/ruby/2.7.0/bin in your PATH,
;           gem executables will not run.


(defn gem
  [context gem-name desired-state]
  (do
    (when-not (has-executable? context "gem")
      (pacman "rubygems" (assoc context :state "present")))
    (let [disabled-errors-context (assoc context :throw-errors false)
          cmd-result (command "gem" ["list" gem-name] disabled-errors-context)
          is-present (clojure.string/includes? (:stdout cmd-result) gem-name)]

      (when (= desired-state "present")
        (when-not is-present
          (command "gem" ["install" gem-name] context))))))

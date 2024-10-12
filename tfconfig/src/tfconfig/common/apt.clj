(ns tfconfig.common.apt
  (:require [tfconfig.common.command :refer [command pre-auth]]))

(defn install-single
  [context package]
  (println (str "Installing " package))
  (command "sudo" ["apt-get" "install" "-y" package] (assoc context :pre-auth true)))

(defn install
  [context packages]
  (dorun (map #(install-single context %) packages)))

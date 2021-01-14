(ns tfconfig.modules.aur.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(def packages ["figlet" "cowsay"])

(def paru-dependencies ["fakeroot" "coreutils" "pkgconf"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % (assoc context :state "present")) paru-dependencies)))

(defn install-paru
  [context]
  (when-not (has-executable? context "paru")
    (println "Installing paru")
    (let [sources-dir (:sources-dir context)
          password (:password context)
          base-dir (str sources-dir "paru")]
      (do
        (dorun (install-dependencies context))
        (dorun (file sources-dir (assoc context :state "dir" :owner (str (:username context) ":"))))
        (command "rm" ["-rf" base-dir] context)
        (command "git" ["clone" "https://aur.archlinux.org/paru.git" base-dir] context)
        (command "makepkg" ["-si" "--noconfirm"] (assoc context :dir base-dir :pre-auth true))))))

(defn install-aur-package
  [context package]
  (println (str "Installing " package))
  (command "paru" ["-S" package "--noconfirm" "--needed" "--sudoloop"] (assoc context pre-auth true)))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (do
    (println "-- Module: AUR --")
    (install-paru context)
    (dorun (map (partial install-aur-package context) packages))))

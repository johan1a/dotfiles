(ns tfconfig.modules.aur
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(def packages ["figlet" "cowsay"])

(defn install-paru
  [sources-dir password]
  (when-not (has-executable? "paru")
    (let [base-dir (str sources-dir "paru")]
      (command "rm" ["-rf" base-dir] {})
      (command "git" ["clone" "https://aur.archlinux.org/paru.git" base-dir] {})
      (command "makepkg" ["-si" "--noconfirm"] {:dir base-dir :pre-auth true :password password})
      )))

(defn install-aur-package
  [package password]
  (println (str "Installing " package))
  (command "paru" ["-S" package "--noconfirm" "--needed" "--sudoloop"] {:pre-auth true :password password}))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (do
    (println "Installing AUR packages")
    (install-paru (:sources-dir context) (:password context))
    (dorun (map #(install-aur-package % (:password context)) packages))))

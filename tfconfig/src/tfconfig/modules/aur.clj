(ns tfconfig.modules.aur
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(defn install-paru
  [sources-dir password]
  (when-not (has-executable? "parux")
    (let [base-dir (str sources-dir "paru")]
      (command "rm" ["-rf" base-dir] {})
      (command "git" ["clone" "https://aur.archlinux.org/paru.git" base-dir] {})
      (command "makepkg" ["-si" "--noconfirm"] {:dir base-dir :pre-auth true :password password})
      )))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (install-paru (:sources-dir context) (:password context)))

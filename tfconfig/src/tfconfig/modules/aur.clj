(ns tfconfig.modules.aur
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(defn install-paru
  [sources-dir]
  (when-not (has-executable? "parux")
    (let [base-dir (str sources-dir "paru")]
      (command "rm" "-rf" base-dir)
      (command "git" "clone" "https://aur.archlinux.org/paru.git" base-dir)
      (command-in-dir base-dir "makepkg" "-si"))))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (install-paru (:sources-dir context)))

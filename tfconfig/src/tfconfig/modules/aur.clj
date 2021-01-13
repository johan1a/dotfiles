(ns tfconfig.modules.aur
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.has-executable :refer :all]))

(def packages ["figlet" "cowsay"])

(defn install-paru
  [context sources-dir password]
  (when-not (has-executable? "paru")
    (println "Installing paru")
    (let [base-dir (str sources-dir "paru")]
      (file sources-dir {:state "dir" :owner "johan:" :verbose (:verbose context)})
      (command "rm" ["-rf" base-dir] context)
      (command "git" ["clone" "https://aur.archlinux.org/paru.git" base-dir] context)
      (command "makepkg" ["-si" "--noconfirm"] {:dir base-dir :pre-auth true :password password :verbose context}))))

(defn install-aur-package
  [context package password]
  (println (str "Installing " package))
  (command "paru" ["-S" package "--noconfirm" "--needed" "--sudoloop"] {:pre-auth true :password password :verbose (:verbose context)}))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (do
    (println "Installing AUR packages")
    (install-paru context (:sources-dir context) (:password context))
    (dorun (map #(install-aur-package context % (:password context)) packages))))

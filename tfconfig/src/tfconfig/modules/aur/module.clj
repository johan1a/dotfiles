(ns tfconfig.modules.aur.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [directory]]
            [tfconfig.common.pacman :refer [pacman]]
            [tfconfig.common.aur :refer [aur-packages]]
            [tfconfig.common.has-executable :refer [has-executable?]]))

(def packages ["fortune-mod"
               "xclip"
               "neofetch"
               "time"
               "ipcalc"
               "tig"
               "remind"
               "light"
               "msr-tools"
               "libffado"
               "lib32-libpulse"
               "python-dbus"
               "fzf"
               "kdeconnect"
               "fd"
               "bat"
               "pandoc-bin"
               "siji-ng"
               "typescript-language-server"
               "bdf-unifont"
               "miscfiles"
               "sharutils" ; uudecode
               "noto-fonts-extra" ; more unicode symbols, ⛧
               "grpcurl-bin"
               "cljfmt-bin"])

; These packages seem to fail when already present?


(def failing-packages ["scalafmt"
                       "clojure-lsp-bin"
                       "vimv"])

(def paru-dependencies ["fakeroot" "coreutils" "pkgconf"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % context :present) paru-dependencies)))

(defn install-paru
  [context]
  (when-not (has-executable? context "paru")
    (println "Installing paru")
    (let [sources-dir (:sources-dir context)
          base-dir (str sources-dir "paru")]
      (dorun (install-dependencies context))
      (dorun (directory context sources-dir {:owner (str (:username context) ":")}))
      (command "rm" ["-rf" base-dir] context)
      (command "git" ["clone" "https://aur.archlinux.org/paru-bin.git" base-dir] context)
      (command "makepkg" ["-si" "--noconfirm"] (assoc context :dir base-dir :pre-auth true)))))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (install-paru context)
  (aur-packages context packages)
  (aur-packages (assoc context :throw-errors false) failing-packages))

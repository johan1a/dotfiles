(ns tfconfig.modules.aur.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [file]]
            [tfconfig.common.pacman :refer [pacman]]
            [tfconfig.common.aur :refer [install-aur-package]]
            [tfconfig.common.has-executable :refer [has-executable?]]))

; TODO is this important: COURSIER_CACHE: ".coursier-cache"
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
               "cadence"
               "lib32-libpulse"
               "python-dbus"
               "carla"
               "qjackctl"
               "fzf"
               "kdeconnect"
               "fd"
               "bat"
               "pandoc-bin"
               "siji-ng"
               "nodejs-vls"
               "nodejs-lts-hydrogen"
               "typescript-language-server"
               "bdf-unifont"
               "stegsolve"
               "miscfiles"
               "sharutils" ; uudecode
               "sleuthkit"
               "zsteg"
               "noto-fonts-extra" ; more unicode symbols, ⛧
               "google-cloud-cli"
               "google-cloud-cli-gke-gcloud-auth-plugin"
               "cnijfilter-common" ; For cups and the Canon pixma iP2850 printer
      ;       linvst-stable #The dummy 'linvst.so' file is located at /usr/share/linvst
        ; httpie
               ])

; These packages seem to fail when already present?
(def failing-packages ["gotop-bin"
                       "metals"
                       "scalafmt"
                       "clojure-lsp-bin"
                       "vimv"])

(def paru-dependencies ["fakeroot" "coreutils" "pkgconf"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % (assoc context :state "present")) paru-dependencies)))

(defn install-paru
  [context]
  (when-not (has-executable? context "paru")
    (println "Installing paru")
    (let [sources-dir (:sources-dir context)
          base-dir (str sources-dir "paru")]
      (dorun (install-dependencies context))
      (dorun (file sources-dir (assoc context :state "dir" :owner (str (:username context) ":"))))
      (command "rm" ["-rf" base-dir] context)
      (command "git" ["clone" "https://aur.archlinux.org/paru-bin.git" base-dir] context)
      (command "makepkg" ["-si" "--noconfirm"] (assoc context :dir base-dir :pre-auth true)))))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (install-paru context)
  (dorun (map (partial install-aur-package context) packages))
  (dorun (map (partial install-aur-package (assoc context :throw-errors false)) failing-packages)))

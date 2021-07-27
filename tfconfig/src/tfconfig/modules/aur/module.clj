(ns tfconfig.modules.aur.module
  (:require [tfconfig.common.command :refer [command]]
            [tfconfig.common.file :refer [file]]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.pacman :refer [pacman]]
            [tfconfig.common.aur :refer [install-aur-package]]
            [tfconfig.common.has-executable :refer [has-executable?]]))

; TODO is this important: COURSIER_CACHE: ".coursier-cache"
(def packages ["fortune-mod"
               "xclip"
               "neofetch"
               "time"
      ; inxi
               "ipcalc"
               "tig"
               "remind"
               "light"
      ;nodejs-lumo-cljs
               "msr-tools"
               "libffado"
               "cadence"
               "lib32-libpulse"
               "python-dbus"
               "carla"
               "qjackctl"
               "pulseaudio-jack"
               "fzf"
               "vimv"
               "kdeconnect"
               "fd"
               "bat"
               ;"dict-freedict-swe-deu-bin" https://download.freedict.org cert expired
               ;"dict-freedict-deu-swe-bin"
               ;"dict-freedict-eng-deu-bin"
               ;"dict-freedict-deu-eng-bin"
               "pandoc-bin"
      ;       linvst-stable #The dummy 'linvst.so' file is located at /usr/share/linvst
      ;       translate-shell
      ;  google-chrome
        ;       stack-bin
        ; httpie
      ;  python-cheat
               ])

; These packages seem to fail when already present?
(def failing-packages ["gotop-bin"
                       "metals"
                       "scalafmt"
                       "jdk11-graalvm-bin"
                       "clojure-lsp-bin"
                       ])

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

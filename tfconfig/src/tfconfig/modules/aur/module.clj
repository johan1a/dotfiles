(ns tfconfig.modules.aur.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.aur :refer :all]
            [tfconfig.common.has-executable :refer :all]))

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
               "gotop-bin"
               "metals"
               "scalafmt"
               "libffado"
               "cadence"
               "lib32-libpulse"
               "python-dbus"
               "carla"
               "qjackctl"
               "pulseaudio-jack"
               "jdk11-graalvm-bin"
               "clojure-lsp-bin"
      ;       linvst-stable #The dummy 'linvst.so' file is located at /usr/share/linvst
      ;       translate-shell
      ;  google-chrome
        ;       stack-bin
        ; httpie
      ;  python-cheat
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
          password (:password context)
          base-dir (str sources-dir "paru")]
      (do
        (dorun (install-dependencies context))
        (dorun (file sources-dir (assoc context :state "dir" :owner (str (:username context) ":"))))
        (command "rm" ["-rf" base-dir] context)
        (command "git" ["clone" "https://aur.archlinux.org/paru-bin.git" base-dir] context)
        (command "makepkg" ["-si" "--noconfirm"] (assoc context :dir base-dir :pre-auth true))))))

(defn run
  "Install an AUR helper and useful packages"
  [context]
  (do
    (install-paru context)
    (dorun (map (partial install-aur-package context) packages))))

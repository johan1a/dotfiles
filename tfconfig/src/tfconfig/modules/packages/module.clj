(ns tfconfig.modules.packages.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.file :refer [directory file-content link]]
   [tfconfig.common.handler :refer [handler]]
   [tfconfig.common.pacman :refer [pacman]]))

(def packages ["base-devel"
               "musl"
               "autoconf"
               "cronie"
               "kubectl"
               "wget"
               "feh"
               "gpicview"
               "htop"
               "iotop"
               "tree"
               "ranger"
               "jack2"
               "vlc"
               "arandr"
               "ttf-dejavu"
               "ttf-inconsolata"
               "udevil"
               "alsa-firmware"
               "alsa-utils"
               "alsa-plugins"
               "pavucontrol"
               "dmenu"
               "the_silver_searcher"
               "strace"
               "fish"
               "blueman"
               "npm"
               "w3m"
               "isync"
               "jdk8-openjdk"
               "openjdk8-doc"
               "openjdk8-src"
               "jdk-openjdk"
               "openjdk-doc"
               "openjdk-src"
               "dialog"
               "ripgrep"
               "wol"
               "ethtool"
               "ttf-inconsolata"
               "lm_sensors"
               "pass"
               "ntp"
               "moreutils"
               "elinks"
               "gnu-free-fonts"
               "ttf-liberation"
               "ttf-droid"
               "clojure"
               "rlwrap"
               "sxiv"
               "lib32-glibc"
               "nodejs"
               "ncdu"
               "arp-scan"
               "xdotool"
               "nfs-utils"
               "gnome-keyring"
               "libsecret"
               "mlocate"
               "fdupes"
               "fail2ban"
               "java-openjfx"
               "task"
               "ledger"
               "parallel"
               "iftop"
               "nethogs"
               "scrot"
               "dos2unix"
               "rsync"
               "figlet"
               "cowsay"
               "dictd"
               "bdf-unifont"
               "hexedit"])
        ; "dnsutils"
        ;  udiskie
        ; redshift

(defn sync-packages
  [context changes]
  (when changes
    (command "pacman" ["-Syu" "--noconfirm"] (assoc context :sudo true))))

(defn enable-multilib
  [context]
    (handler context :multilib-enabled sync-packages)
    ; TODO don't think this works properly
    (file-content (assoc context :handler-ref :multilib-enabled) "/etc/pacman.conf" "Activate multilib" ["[multilib]" "Include = /etc/pacman.d/mirrorlist"]))

(defn config-makepkg
  [context]
    (let [home (:home context)
          owning-context (assoc context :sudo true :owner (str (:username context) ":"))]
      (directory owning-context (str home ".config/pacman"))
      (link context (<< "~(:modules-dir context)packages/files/makepkg.conf") (str home ".config/pacman/makepkg.conf"))))

; TODO fix, move and refactor
; (defn enable-wallpaper
;   [context]
;   (let [path (str (:home context) ".fehbg")
;         lines ["#!/bin/sh" "feh --no-fehbg --bg-scale '/home/johan/pictures/wallpapers/yosemite-2560x1440-5k-4k-wallpaper-8k-forest-osx-3955.jpg'"]]
;     (when-not (file-exists? path)
;       (do
;         (file path (assoc context :state "file" :executable true))
;         (file-content context path "Default wallpaper" lines)
;         (command path [] context)))))

(defn run
  "Installs useful packages"
  [context]
    (enable-multilib context)
    (config-makepkg context)
    (dorun (map #(pacman % (assoc context :state "present")) packages))
    (let [sudo-context (assoc context :sudo true)]
      (command "archlinux-java" ["set" "java-14-openjdk"] (assoc sudo-context :throw-errors false)) ; TODO move
      (when-not (:ci context)
        (command "systemctl" ["enable" "cronie"] sudo-context)
        (command "systemctl" ["restart" "cronie"] sudo-context)
        (command "systemctl" ["enable" "bluetooth"] sudo-context)
        (command "systemctl" ["start" "bluetooth"] sudo-context))))

(ns tfconfig.modules.mail.module
  (:require [tfconfig.common.file :refer [link directory]]
            [tfconfig.common.pacman :refer [pacman]]
            [tfconfig.common.cron :refer [cron]]
            [tfconfig.common.aur :refer [install-aur-package]]
            [clojure.core.strint :refer [<<]]))

(defn create-dirs
  [context]
  (let [home (:home context)
        owning-context (assoc context :sudo true :owner (str (:username context) ":"))]
      (directory owning-context (str home ".mutt"))
      (directory owning-context (str home ".mutt/scripts"))
      (directory owning-context (str home ".mail/protonmail"))
      (directory owning-context (str home ".config/khal"))
      (directory owning-context (str home ".config/vdirsyncer"))
      (directory owning-context (str home ".config/khard"))))

(defn link-files
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)mail/files")
        bin-dir (str home ".local/bin")
        executable-context (assoc context :executable true)]
      (link context (str files-dir "/mbsyncrc") (str home ".mbsyncrc"))
      (link context (str files-dir "/muttrc") (str home ".mutt/muttrc"))
      (link executable-context (str files-dir "/view_attachment.sh") (str home ".mutt/scripts/view_attachment.sh"))
      (link executable-context (str files-dir "/open") (str bin-dir "/open"))
      (link executable-context (str files-dir "/notmuch-hook") (str bin-dir "/notmuch-hook"))
      (link executable-context (str files-dir "/mbsync-launcher") (str bin-dir "/mbsync-launcher"))
      (link context (str files-dir "/mailcap") (str home ".mailcap"))
      (link context (str files-dir "/mailboxes") (str home ".mutt/mailboxes"))
      (link context (str files-dir "/notmuch-config") (str home ".notmuch-config"))
      (link context (str files-dir "/khal-config") (str home ".config/khal/config"))
      (link context (str files-dir "/vdirsyncer-config") (str home ".config/vdirsyncer/config"))
      (link context (str files-dir "/khard.conf") (str home ".config/khard/khard.conf"))))

(defn run
  [context]
    (let [sudo-context (assoc context :sudo true)]
      (pacman "neomutt" (assoc sudo-context :state "present"))
      (pacman "notmuch" (assoc sudo-context :state "present"))
      (create-dirs context)
      (install-aur-package context "vdirsyncer")
      (install-aur-package (assoc context :throw-errors false) "protonmail-bridge")
      (install-aur-package context "khal")
      (install-aur-package context "khard")
      (link-files context)
      (when-not (:ci context)
        (cron context "Sync mails" "*/3 * * * * ~/.local/bin/mbsync-launcher >> ~/.local/log/mbsync.log 2>&1")
        (cron context "Sync calendar" "*/5 * * * * /usr/bin/vdirsyncer sync >> ~/.local/log/vdirsyncer.log 2>&1"))))


(ns tfconfig.modules.neomutt.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [tfconfig.common.cron :refer :all]
            [tfconfig.common.aur :refer :all]
            [clojure.core.strint :refer [<<]]))

(defn create-dirs
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)neomutt/files")
        owning-context (assoc context :sudo true :owner (str (:username context) ":"))]
    (do
      (directory owning-context (str home ".mutt"))
      (directory owning-context (str home ".mutt/scripts"))
      (directory owning-context (str home ".mail/gmail")))))

(defn link-files
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)neomutt/files")
        bin-dir (str home ".local/bin")
        executable-context (assoc context :executable true)]
    (do
      (link context (str files-dir "/mbsyncrc") (str home ".mbsyncrc"))
      (link context (str files-dir "/muttrc") (str home ".mutt/muttrc"))
      (link executable-context (str files-dir "/view_attachment.sh") (str home ".mutt/scripts/view_attachment.sh"))
      (link executable-context (str files-dir "/open") (str bin-dir "/open"))
      (link executable-context (str files-dir "/notmuch-hook") (str bin-dir "/notmuch-hook"))
      (link executable-context (str files-dir "/mbsync-launcher") (str bin-dir "/mbsync-launcher"))
      (link context (str files-dir "/mailcap") (str home ".mailcap"))
      (link context (str files-dir "/mailboxes") (str home ".mutt/mailboxes"))
      (link context (str files-dir "/notmuch-config") (str home ".notmuch-config")))))

(defn run
  [context]
  (do
    (let [sudo-context (assoc context :sudo true)]
      (pacman "neomutt" (assoc sudo-context :state "present"))
      (pacman "notmuch" (assoc sudo-context :state "present"))
      (create-dirs context)
      (link-files context)
      (when-not (:ci context)
        (cron context "Sync mails" "*/3 * * * * ~/.local/bin/mbsync-launcher >> ~/.local/log/mbsync.log 2>&1")))))


(ns tfconfig.modules.i3-gaps.module
  (:require [tfconfig.common.command :refer :all]
            [tfconfig.common.file :refer :all]
            [tfconfig.common.pacman :refer :all]
            [tfconfig.common.has-executable :refer :all]
            [tfconfig.common.handler :refer :all]
            [clojure.core.strint :refer [<<]]))

(def dependencies ["i3-gaps" "i3status" "i3lock" "imagemagick"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % (assoc context :state "present")) dependencies)))

(defn setup-links
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)i3_gaps/files")]
    (link context (<< "~{files-dir}/logind.conf") "/etc/systemd/logind.conf" {:sudo true})
    (link context (<< "~{files-dir}/is-tmux") (<< "~{home}.local/bin/is-tmux"))
    (link context (<< "~{files-dir}/i3") (<< "~{home}.config/i3"))
    (link (assoc context :executable true) (<< "~(:modules-dir context)packages/files/bin/i3-after-hook") (str (:home context) ".local/bin/i3-after-hook"))))

(defn install-i3lock
  [context]
  (let [source-dir (str (:sources-dir context "i3lock-fancy-multimonitor"))]
    (when-not (dir-exists? context source-dir)
      (command "git" ["clone" "https://github.com/guimeira/i3lock-fancy-multimonitor.git" source-dir] context)
      (file (str source-dir "i3lock-fancy-multimonitor/lock") (assoc context :executable true)))))

(defn run
  [context]
  (do
    (install-dependencies context)
    (setup-links context)
    (install-i3lock context)))

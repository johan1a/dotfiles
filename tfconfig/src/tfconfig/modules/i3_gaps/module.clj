(ns tfconfig.modules.i3-gaps.module
  (:require [tfconfig.common.command :refer :all])
  (:require [tfconfig.common.file :refer :all])
  (:require [tfconfig.common.pacman :refer :all])
  (:require [tfconfig.common.has-executable :refer :all])
  (:require [tfconfig.common.handler :refer :all]))

(def dependencies ["i3-gaps" "i3status" "i3lock" "imagemagick"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % (assoc context :state "present")) dependencies)))

(defn setup-links
  [context]
    (let [files-dir (str (:modules-dir context) "i3_gaps/files")
          logind-conf (str files-dir "/logind.conf")]
          (file "/etc/systemd/logind.conf" (assoc context :state "link" :src logind-conf))
          (file (str (:home context) ".local/bin/is-tmux") (assoc context :state "link" :src (str files-dir "/is-tmux")))
          (file (str (:home context) ".config/i3") (assoc context :state "link" :src (str files-dir "/i3")))))

(defn install-i3lock
  [context]
  (let [source-dir (str (:sources-dir context "i3lock-fancy-multimonitor"))]
       (when-not (dir-exists? context source-dir)
         (command "git" ["clone" "https://github.com/guimeira/i3lock-fancy-multimonitor.git" source-dir] context)
         (file (str source-dir "i3lock-fancy-multimonitor/lock") (assoc context :executable true)))))

(defn run
  [context]
  (do
    (println "-- Module: i3-gaps --")
    (install-dependencies context)
    (setup-links context)
    (install-i3lock context)))

  ; - name: Check if i3lock-fancy-multimonitor exists
  ;   stat: path="{{ user_home }}/source/i3lock-fancy-multimonitor"
  ;   register: lockdir

  ; - name: Pull i3lock-fancy-multimonitor source
  ;   git: repo=https://github.com/guimeira/i3lock-fancy-multimonitor.git
  ;        dest="{{ user_home }}/source/i3lock-fancy-multimonitor"
  ;   register: i3lockfancymulti_git
  ;   when: not lockdir.stat.exists

  ; - name: Set run permissions
  ;   file:
  ;     path: "{{ user_home }}/source/i3lock-fancy-multimonitor/lock"
  ;     mode: a+x


(ns tfconfig.modules.i3-gaps.module
  (:require
   [clojure.core.strint :refer [<<]]
   [tfconfig.common.command :refer [command]]
   [tfconfig.common.file :refer [dir-exists? file link]]
   [tfconfig.common.pacman :refer [pacman]]))

(def dependencies ["i3-gaps" "i3status" "i3lock" "imagemagick"])

(defn install-dependencies
  [context]
  (dorun (map #(pacman % context :present) dependencies)))

(defn setup-links
  [context]
  (let [home (:home context)
        files-dir (<< "~(:modules-dir context)i3_gaps/files")]
    (link context (<< "~{files-dir}/logind.conf") "/etc/systemd/logind.conf" {:sudo true})
    (link context (<< "~{files-dir}/is-tmux") (<< "~{home}.local/bin/is-tmux"))
    (link context (<< "~{files-dir}/i3") (<< "~{home}.config/i3"))
    (link context (<< "~(:modules-dir context)packages/files/bin/i3-after-hook") (str (:home context) ".local/bin/i3-after-hook") {:executable true})))

(defn install-i3lock
  [context]
  (let [source-dir (<< "~(:sources-dir context)i3lock-fancy-multimonitor")]
    (when-not (dir-exists? context source-dir)
      (command "git" ["clone" "https://github.com/guimeira/i3lock-fancy-multimonitor.git" source-dir] context)
      (file context (str source-dir "/lock") {:executable true}))))

(defn run
  [context]
  (install-dependencies context)
  (setup-links context)
  (install-i3lock context))

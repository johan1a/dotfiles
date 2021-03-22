(ns tfconfig.modules.ime.module
  (:require [tfconfig.common.aur :refer [install-aur-packages]]
            [tfconfig.common.file :refer [link directory]]
            [clojure.core.strint :refer [<<]]))

(defn run
  "Sets up IME for Chinese"
  [context]
  (install-aur-packages context ["fcitx-im" "fcitx-libpinyin" "fcitx-configtool"])
  (let [files-dir (<< "~(:modules-dir context)ime/files")
        home (:home context)]
    (directory context (<< "~{home}/.config/fcitx"))
    (link context (<< "~{files-dir}/config") (str home ".config/fcitx/config"))
    (link context (<< "~{files-dir}/profile") (str home ".config/fcitx/profile"))
    (link context (<< "~{files-dir}/fcitx-env") (str home ".config/fcitx/fcitx-env"))))


(ns tfconfig.modules.wake-on-lan.module
  (:require [clojure.core.strint :refer [<<]]
            [tfconfig.common.command :refer [command]]))

(defn run
  [context]
  (let [output (command "sh" [(<< "~(:modules-dir context)wake_on_lan/files/get_mac_address.sh")] context)
        mac-address (first (:stdout output))
        src (<< "~(:modules-dir context)wake_on_lan/files/50-wired.link")
        dest "/etc/systemd/network/50-wired.link"
        ]
        (do
          (command "cp" [src dest] context :sudo)
          (command "sed" ["-i" (<< "s/MAC_ADDRESS/~{mac-address}/g") dest] context :sudo))))

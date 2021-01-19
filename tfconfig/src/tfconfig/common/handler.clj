(ns tfconfig.common.handler)

(defn handler
  [context handled-key callback]
  (add-watch (:changes context) handled-key
             (fn [the-key watched old-state new-state]
               (when (= the-key handled-key)
                 (callback context (the-key new-state))))))

(defn notify
  [context event]
  (let [changes (:changes context)
        handler-ref (:handler-ref context)]
    (when handler-ref
      (swap! changes
             (fn [current-state]
               (update-in current-state [handler-ref] #(conj % event)))))))


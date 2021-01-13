(ns tfconfig.common.handler)

(defn handler
  [context handled-key callback]
    (add-watch (:changes context) handled-key
               (fn [key watched old-state new-state]
                 (when (= key handled-key)
                   (callback context (key new-state))))))

(defn notify
  [context event]
  (let [changes (:changes context)
        handler-ref (:handler-ref context)]
    (swap! changes
           (fn [current-state]
             (update-in current-state [handler-ref] #(conj % event))))))


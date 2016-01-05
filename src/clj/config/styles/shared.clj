(ns styles.shared)

(alter-meta! *ns*
             (fn [m] (assoc m
                            :co-ns true
                            :resource-type :polymer-style-module)))


(def uri "styles/shared.html")

(def psk "psk-style")                   ; a dom-module in styles/shared.html

(ns scripts)

(alter-meta! *ns*
             (fn [m] (assoc m :co-ns true :resource-type :js)))

(def polyfill-lite
  "bower_components/webcomponentsjs/webcomponents-lite.js")

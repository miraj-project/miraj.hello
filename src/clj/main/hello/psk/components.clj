(ns hello.psk.components
  (:require [polymer.dom :as dom]
            [Polymer :refer :all]
            [Polymer.Events]
            [miraj.html :as h]))

(alter-meta! *ns*
             (fn [m] (assoc m
                            :co-ns true
                            :resource-type :polymer
                            :resource-pfx "")))

(h/def-cotype my-greeting
  "my-greeting custom component"
  [^{:type String, :value "Welcome!", :notify true}
   greeting]
  (h/co-dom
   (h/import  '(styles.shared shared-styles))
   (h/style ":host {display: block;}")
   (h/h2 ::special.page-title :greeting)
   (h/span ::.paper-font-body2 "Update text to change the greeting.")
   ;; Listens for "input" event and sets greeting to <input>.value
   (h/input {:class "paper-font-body2" :value :input->greeting}))
  Polymer.Lifecycle
  (created [] "console.log('MY GREETINGS created!')")
  (attached [] "console.log('MY GREETINGS attached!')")
  Polymer.Events.Gesture
  (with-element :special (tap [] "console.log('you tapped the h1 element')"))
  (down [x] "console.log('DOWN'); console.log('AGAIN')"))

(h/def-cotype my-list
  "my-list docstring"
  [^{:type Array, :notify true} items]
  (h/co-dom
   (h/style ":host {display: block;}")
   (h/ul
    (h/template {:is "dom-repeat" :items :items}
                (h/li (h/span ::.paper-font-body1 :item)))))
  Polymer.Lifecycle
   (ready [] "this.items = ['Responsive Web App boilerplate',
                           'Iron Elements and Paper Elements',
                           'End-to-end Build Tooling (including Vulcanize)',
                           'Unit testing with Web Component Tester',
                           'Routing with Page.js',
                           'Offline support with the Platinum Service Worker Elements'];"))

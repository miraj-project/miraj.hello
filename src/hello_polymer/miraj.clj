(ns hello-polymer.miraj
  (:require [miraj.html :as h]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]] :reload-all))

(def home-meta
  {:platform {:apple {:mobile-web-app {:capable true
                                       :status-bar-style :black
                                       :title "Hello"}}
              :ms {:navbutton-color "#FFCCAA"
                   :tile-color "#3372DF"
                   :tile-image "images/ms-touch-icon-144x144-precomposed.png"}
              :mobile {:agent {:format :html5
                               :url "http://example.org/"}
                       :web-app-capable true}}})

(def home-html
  (h/html
   (h/require '[polymer.paper :as paper :refer [button card]]
              '[polymer.iron :as iron :refer [icon icons]])

   ;; PROBLEM: distinguish css refs from shared style refs
   ;; delegate to config spec?
   (h/import #_(styles hello world)
             #_(html-imports hello world)
             '(styles.shared foo bar))
             '(scripts polyfill-lite))

   (h/body
    (h/h1 "Hello Polymer!")
    (h/div (iron/icon {:icon "menu"}))
    (h/div {:id "cards"}
           (paper/card {:heading "Card Title"}
                       (h/div {:class "card-content"} "Some content")
                       (h/div {:class "card-actions"}
                              (paper/button {:raised nil} "Some action")))))))


(println "home-html")
(miraj.markup/serialize home-html)

(def homepage
  (h/optimize :js
              (h/normalize home-html)))

             ;; (with-meta home-html home-meta))))

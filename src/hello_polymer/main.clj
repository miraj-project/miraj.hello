(ns hello-polymer.main
  (:require [miraj.html :as h]
            [polymer.paper :as paper]
            [hello-polymer.miraj :as hm]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]] :reload-all))


(def homepage
  (h/html
   (h/head
    (h/script {:src "bower_components/webcomponentsjs/webcomponents-lite.js"})
    (h/platform {:apple {:mobile-web-app {:capable true
                                          :status-bar-style :black
                                          :title "Hello"}}
                 :ms {:navbutton-color "#FFCCAA"
                      :tile-color "#3372DF"
                      :tile-image "images/ms-touch-icon-144x144-precomposed.png"}
                 :mobile {:agent {:format :html5
                                  :url "http://example.org/"}
                          :web-app-capable true}})
    (h/link {:rel "stylesheet" :type "text/css" :href "styles/hello.css"})

    (h/link {:rel "import" :href "bower_components/paper-button/paper-button.html"})
    (h/link {:rel "import" :href "bower_components/paper-card/paper-card.html"}))

   (h/body
    (h/h1 "Hello Polymer!")
    (h/div {:id "cards"}
           (paper/card {:heading "Card Title"}
                       (h/div {:class "card-content"} "Some content")
                       (h/div {:class "card-actions"}
                              (paper/button {:raised nil} "Some action")))))))

(defroutes app-routes
  (GET "/" []
       (miraj.markup/serialize homepage))

  (GET "/miraj" []
       (miraj.markup/serialize hello-polymer.miraj/homepage))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

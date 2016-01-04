(ns hello.primitives
  (:require [miraj.html :as h]
            ;; [polymer.paper :as paper]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def homepage-html
  (h/html
   (h/head
    (h/meta-map
     {:title "hello.primitives demo"
      :description "this page demonstrates basic usage of miraj.html"
      :platform {:apple {:mobile-web-app {:capable true
                                                     :status-bar-style :black
                                                     :title "Hello"}}
                            :ms {:navbutton-color "#FFCCAA"
                                 :tile-color "#3372DF"
                                 :tile-image "images/ms-touch-icon-144x144-precomposed.png"}
                            :mobile {:agent {:format :html5
                                             :url "http://example.org/"}
                                     :web-app-capable true}}})
    (h/link {:rel "stylesheet" :type "text/css" :href "styles/hello.css"})
    (h/script "// I don't do much, but I'll be moved to the bottom of body if you optimize.")
    (h/script {:src "bower_components/webcomponentsjs/webcomponents-lite.js"}
              "//I'll be made first script elt in <head> if you optimize."))
   (h/body
    (h/h1 "Hello, with miraj.html primitives!")
    (h/div {:id "cards"}
           (h/div {:heading "Card Title"}
                  (h/div {:class "card-content"} "Some content")
                  (h/style ".card-content {background:#EEEEFF;}")
                  (h/div {:class "card-actions"}
                         (h/button {:raised nil} "Some action")))))))

(defn homepage
  []
  (let [result (->> homepage-html
                    h/normalize
                    h/optimize
                    h/serialize)]
    result))


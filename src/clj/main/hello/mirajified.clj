(ns hello.mirajified
  (:require [miraj.html :as h]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def home-meta
  ;; <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes">

  ;;FIXME - no hardcoded values, always use indirection.
  ;; e.g. not :tile-color "#3372DF" but :tile-color :foo
  ;; ditto esp. for URIs
  ;; for this we need a config namespace, just like for js and css resources
  ;; for images, the schema will tell us which vals are supposed to be URIs,
  ;; so we will then look up the kw in the images namespace.  ?
  ;; ditto for colors - define a color namespace
  ;; what about literals? we should already use indirection, for i18n
  ;; or we could use a single meta namespace
  {:title "hello.mirajified demo"
   :description "this page demonstrates usage of miraj.html and polymer"
   :platform {:apple {:mobile-web-app {:capable true
                                       :status-bar-style :black
                                       :title :hello-str} ;; "Hello"
                      :touch {:icon {:uri "/images/touch/chrome-touch-icon-192x192.png"}}}
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
     (h/import '(styles hello world)
               '(scripts polyfill-lite-min))
     (h/body
      (h/h1 "Hello Polymer!")
      (h/div (iron/icon {:icon "menu"}))
      (h/div ::cards
             (paper/card {:heading "Hello, you ol' Card!"}
                         (h/div {:class "card-content"} "Some content")
                         (h/div {:class "card-actions"}
                                (paper/button {:raised nil} "Some action")))))))

(defn homepage
  []
  (-> home-html
      (with-meta home-meta)
      h/normalize
      h/optimize
      h/serialize))



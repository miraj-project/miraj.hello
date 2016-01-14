(ns hello.psk.ajax
  (:require [miraj.html :as h]
            ;; [polymer.iron :as iron :refer [flex-layout icons pages selector]]
            ;; [polymer.paper :as paper :refer [drawer-panel icon-button item material menu
            ;;                                  scroll-header-panel toast toolbar]]
            [polymer.platinum.service-worker :as sw :refer [cache register]]
            ;; [hello.psk.components :as comp :refer [my-greeting my-list]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(h/require '[polymer.iron :as iron :refer [ajax image]])

;;            '[polymer.paper :as paper :refer [drawer-panel icon-button item material menu
;;                                              scroll-header-panel toast toolbar]]
;;             ;; [polymer.platinum.service-worker :as sw :refer [cache register]]
;;            ;; [polymer.platinum :as plat :refer [sw-cache sw-register]]
;;            '[hello.psk.components :as comp :refer [my-greeting my-list]])

  ;; <script src="../../webcomponentsjs/webcomponents-lite.js"></script>
  ;; <link rel="import" href="../iron-ajax.html">
  ;; <link rel="import" href="../../iron-image/iron-image.html">
  ;; <link rel="import" href="../../paper-styles/demo-pages.html">

(def ajax-html
  (h/html {:lang "en"}
          (h/head
           (h/require '[polymer.iron :as iron :refer [ajax image]])
           (h/meta {:name "title" :content "iron-ajax"})
           (h/style "
    iron-image {
      background-color: lightgray;
      margin: 1em;
    }
    .horizontal-section {
      max-width: 300px;
      margin-bottom: 24px;
    }"))
          (h/body {:unresolved nil}
                  (h/h1 "Video Feed")
                  (h/div ::.horizontal-section-container
                         (h/template {:is "dom-bind" :id "app"}
                                     (iron/ajax {:auto nil
                                                 :url "https://www.googleapis.com/youtube/v3/search"
                                                 :params "{\"part\":\"snippet\",
 \"q\":\"polymer\",
 \"key\": \"AIzaSyAuecFZ9xJXbGDkQYWBmYrtzOGJD-iDIgI\",
 \"type\": \"video\"}"
                                                 :handle-as "json"
                                                 :last-response "{{ajaxResponse}}"})
                                     (h/template
                                      {:is "dom-repeat" :items 'ajaxResponse.items}
                                      (h/div ::.horizontal-section
                                             (h/h2 (h/a {:href "[[url(item.id.videoId)]]"
                                                         :target "_blank"}
                                                        'item.snippet.title))
                                             (iron/image {:src "[[item.snippet.thumbnails.high.url]]"
                                                          :width 256
                                                          :height 256
                                                          :preload nil
                                                          :fade nil
                                                          :sizing "cover"
                                                          })
                                             (h/p "[[item.snippet.description]]")))))
                  (h/script "
    var app = document.querySelector('#app');

    app.url = function (videoId) {
      return 'https://www.youtube.com/watch?v=' + videoId;
    }; "))))

(defn homepage
  [x]
  (println "AJAX HOMEPAGE: " x)
  (-> ajax-html
      h/normalize
      h/optimize
      h/serialize))

;; (homepage nil)

;; (h/pprint (h/normalize ajax-html))

;; (miraj.markup/co-compile "ajaxtest.html"
;;                          (-> ajax-html
;;                              h/normalize
;;                              h/optimize)
;;                          :pprint)

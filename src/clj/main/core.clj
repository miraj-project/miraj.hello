(ns core
  (:require [hello.miraj.html.core :as h]
            [hello.miraj.polymer.core :as p]
            [hello.psk.core :as psk]
            [hello.psk.ajax :as ajax]
            [hello.psk.components :as hello]
            [miraj.markup :as miraj]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  ;; (GET "/" [] index.html)

  (GET "/html" []
       (h/homepage))

  (GET "/polymer" []
       (p/homepage))

  (GET "/psk" []
       psk/homepage)

  ;; (GET "/ajax" []
  ;;      ajax/homepage)

  (GET "/hello/psk/components/my-greeting" []
       (let [result (miraj/<<! hello.psk.components/my-greeting)]
         (println "GET: " result)
         result))

    (GET "/hello/psk/components/my-list" []
       (let [result (miraj/<<! hello.psk.components/my-list)]
         (println "GET: " result)
         result))

  (route/not-found "NOT FOUND"))

(def app
  (-> (wrap-defaults app-routes site-defaults)
      (wrap-resource "/")))
 

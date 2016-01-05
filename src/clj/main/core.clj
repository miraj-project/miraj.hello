(ns core
  (:require [hello.miraj.html.core :as h]
            [hello.miraj.polymer.core :as p]
            [hello.psk.core :as psk]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  ;; (GET "/" [] index.html)

  (GET "/html" []
       (h/homepage))

  (GET "/polymer" []
       (p/homepage))

  (GET "/psk" []
       psk/homepage)

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


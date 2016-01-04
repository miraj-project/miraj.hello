(ns hello.main
  (:require [hello.primitives :as prim]
            [hello.mirajified :as m]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  ;; (GET "/" [] index.html)

  (GET "/primitives" []
       (hello.primitives/homepage))

  (GET "/miraj" []
       (hello.mirajified/homepage))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


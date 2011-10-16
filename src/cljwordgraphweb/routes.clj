(ns cljwordgraphweb.routes
  (:use compojure.core
        cljwordgraphweb.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (POST "/graph" [text] (graph-page text))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      wrap-base-url))

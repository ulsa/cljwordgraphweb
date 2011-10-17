(ns cljwordgraphweb-ring.core
  (:use [clojure.pprint :only (pprint)]))

(defn app [req]
  (let [uri (:uri req)]
    {:status 200
     :body (with-out-str (clojure.pprint/pprint req))}))

(defn app2 [req]
  (let [uri (:uri req)]
    (if (= uri "/")
      {:status 200
       :body "found"}
      {:status 404
       :body (str "no mapping for " uri)})))

(defn app3 [req]
  (let [uri (:uri req)]
    (if (= uri "/")
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body "
<html>
  <body>
    <form action='/graph' method='POST'>
      <input type='text' name='text' />
      <br />
      <input type='submit' name='Graph' />
    </form>
  </body>
</html>"}
      {:status 404
       :body (str "no mapping for " uri " with query-string '" (:query-string req) "'")})))

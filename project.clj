(defproject cljwordgraphweb "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [compojure "0.6.0"]
                 [hiccup "0.3.6"]
                 [cljwordgraph "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[lein-ring "0.4.6"
                      :exclusions [org.mortbay.jetty/servlet-api]]]
  :ring {:handler cljwordgraphweb.routes/app})

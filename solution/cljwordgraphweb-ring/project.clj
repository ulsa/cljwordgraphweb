(defproject cljwordgraphweb-ring "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [cljwordgraph "1.0.0-SNAPSHOT"]
                 [ring/ring-core "0.3.11"]]
  :dev-dependencies [[lein-ring "0.4.6"
                      :exclusions [org.mortbay.jetty/servlet-api]]]
  :ring {:handler cljwordgraphweb-ring.core/app})

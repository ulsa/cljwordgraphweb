(ns cljwordgraphweb.views
  (:use cljwordgraph.core
        [hiccup core page-helpers form-helpers]))

(defn graph-text [text]
  (-> text
      gather-words
      count-words
      sort-counted-words
      histogram))

(defn index-page []
  (html5
   [:head
    [:title "WordGraph Web"]
    (include-css "/css/style.css")]
   [:body
    [:h1 "Enter text to parse:"]
    (form-to [:post "/graph"] (text-area "text") [:br] (submit-button "Graph"))]))

(defn graph-page [text]
  (html5
   [:head
    [:title "WordGraph Web"]
    (include-css "/css/style.css")]
   [:body
    [:h1 "The text to parse is:"]
    [:p text]
    [:h1 "The parsed text is:"]
    [:pre (graph-text text)]]))

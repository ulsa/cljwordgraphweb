# cljwordgraphweb

A web front-end for the Clojure WordGraph implementation.

## Usage

Start the server:

   lein ring server

Browse to [http://localhost:3000]()

## License

Copyright (C) 2011 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

# Lab

In this lab we will begin with the basics and first learn how to
create a Clojure web application based on the Ring framework. The HTML
is generated simply by creating strings.

We will then use Hiccup to create the HTML. Hiccup is a HTML generation
framework. We'll start with the core functionality, then refactor to
use some of the available helpers.

Then we will refactor this to use the Compojure framework, and see
what benefits it gives us.

Finally, we will transform the Hiccup-generated HTML into plain HTML
pages, which we will use as templates. These templates will be used by
the Enlive framework, which will locate the places where we want
dynamic data and inject it there.

## Ring

Ring is a web framework that is simplicity itself. It does two things:

1. Transforms a HTTP request into a Clojure map, and a Clojure map
into a HTTP response
2. Provides middleware functionality as function wrappers

Ring handlers are plain Clojure functions that take a map and return a
map. Ring takes a HTTP request, turns it into a map, passes it into a
handler, then takes the returned map and turns it into a HTTP response,
which is then returned to the client. Here's an example:

    (defn myapp [req]
      {:status 200
       :body "<html><body><h3>Hello world</h3></body></html>"})

A Ring wrapper is a plain Clojure function that takes a handler
function (and possibly more args) and returns a new handler function,
ie a function that takes a map and returns a map. Here's an example of
a wrapper that adds the given `Content-Type` to each response:

    (defn wrap-content-type [handler content-type]
      (fn [request]
        (let [response (handler request)]
          (assoc-in response [:headers "Content-Type"]
                    content-type)))) 

Here's how you use it. You simply wrap it around your original handler:

    (def app
      (wrap-content-type handler "text/html"))

However, using more than one wrapper quickly becomes hard to read:

    (def app
      (wrap-keyword-params (wrap-params (wrap-content-type handler
                                                           "text/html"))))

The threading macro, `->`, helps making the usage of wrappers more
readable: 

    (def app
      (-> handler
          wrap-content-type "text/html"
          wrap-params
          wrap-keyword-params))

Exercise: Write a Ring handler that listens to a GET on "/" and
responds with a form with action "/graph" containing a text-field and
a submit button

Exercise: Write a function that takes a text and passes it to
`cljwordgraph`

Exercise: Add to the handler so that it listens to /graph, takes the
text parameter and passes it to the function you just wrote

## Hiccup

Generating common HTML elements over and over again is tedious and
error-prone. Hiccup provides a toolbox of convenience functions for
creating most common elements, such as text fields, submit buttons,
drop-downs and forms, but also common operations such as encoding and
escaping.

You can try it out yourself. Open a REPL from a Leiningen project that
has Hiccup as dependency:

    $ lein repl
    user=> (html [:p "Hello world"])
    "<p>Hello world</p>"

The HTML we used in the `myapp` handler above looked like this:

    "<html><body><h3>Hello world</h3></body></html>"

It could be generated like this when using Hiccup:

    (html
      [:html
        [:body
          [:h3 "Hello world"]])

Exercise: Replace the manual HTML-generation with Hiccup core
functions

### Hiccup Page Helpers

The `page-helpers` namespace contains some convenience functions for
encoding, including, linking and list handling. The `html5` macro, for
example, sets an appropriate DOCTYPE and provides `html` tags. The
above code can thus be shortened even further:

    user=> (html5 [:body [:h3 "Hello world]])
    "<!DOCTYPE html>\n<html><body><h3>Hello world</h3></body></html>"

Other useful page helpers are:

* html4
* include-css
* include-js
* javascript-tag
* link-to
* ordered-list
* unordered-list
* url

Exercise: Rewrite the HTML-generating using Hiccup page-helpers

### Hiccup Form Helpers

The `form-helpers` namespace contains functions for creating HTML
fields related to HTML form handling.

Using plain Hiccup to create a form looks like this:

    [:form {:action "/login" :method :post}
     [:input {:type :input :name username"}]
     [:br]
     [:input {:type :password :name password"}]
     [:br]
     [:input {:type :submit :value "Login"}]]

Using the Hiccup form helpers, it looks like this:

    (form-to [:post "/login"]
             (text-field "username")
             [:br]
             (text-field "password")
             [:br]
             (submit-button "Login"))

Exercise: Rewrite the `cljwordgraphweb` form code using Hiccup form helpers

## Compojure

Compojure builds upon Ring and adds mainly routing functionality. The
`defroutes` macro takes a sequence of routing declarations and
delegates to Ring for the request and response transformations. 

Routing a GET on a root URL with no parameters to a handler function
looks like this in Compojure:

    (defroutes main-routes
      (GET "/" [] (index-page)))

## Enlive

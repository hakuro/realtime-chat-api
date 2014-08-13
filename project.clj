(defproject realtime-chat-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [compojure "1.1.8"]
                 [aleph "0.3.3"]
                 [clojure-msgpack "0.1.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler realtime-chat-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})

(ns realtime-chat-api.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.tools.logging :as logging]
            [lamina.core :refer :all]
            [aleph.http :refer :all]
            [environ.core :refer [env]]))

(def broadcast-channel (channel))

(defn handler [ch handshake]
  (receive-all ch (fn [msg] (enqueue broadcast-channel msg)))
  (receive-all broadcast-channel (fn [msg] (enqueue ch msg))))

(defonce server (atom nil))

(defn start [port]
  (let [port (Integer. (or port (env :port) 5000))]
    (reset! server
            (start-http-server handler
                               {:port port
                                :websocket true}))
    (logging/info (str "Started with port " port))))

(defn stop []
  (when @server
    (@server)
    (reset! server nil)))

(defn init [port] (start port))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

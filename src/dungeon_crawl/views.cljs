(ns dungeon-crawl.views
  (:require [re-frame.core :refer [subscribe] :as re-frame]
            [dungeon-crawl.consts :refer [sprites] :as consts]
            [dungeon-crawl.events]
                        [taoensso.timbre :as t  ]

            ))
(def log (.-log js/console))

;;:style\s"fill:(.+?);"


(defn hero [[x y]]
  [:use { :x x, :y y, :xlinkHref "#hero"}])

(defn random-room-dimensions [] [ (+ 300 (rand-int 200)) (+ 200 (rand-int 100))])



(defn generate-room [width  height]
  (let [hero-position (:position @(subscribe [:hero])) ]
       [:svg {:width  width,
             :height height,
             :style {:background-color "lightblue" }}
       [sprites]
       [:g [hero hero-position]]]))


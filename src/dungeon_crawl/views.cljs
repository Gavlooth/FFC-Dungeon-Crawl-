(ns dungeon-crawl.views
  (:require [re-frame.core :refer [subscribe] :as re-frame]
            [dungeon-crawl.consts :refer [sprites] :as consts]))

;;:style\s"fill:(.+?);"

(defn hero [[x y]]
  [:use { :x x, :y y, :xlinkHref "#hero"}])


(defn generate-room [hero-poss]
  ( let [width (+ 300 (rand-int 200))   height (+ 100 (rand-int 200))  ]
    [:svg {:width  width
           :height height}
     [sprites]
     [:g [:rect {:width        width
                 :height       height
                 :class        "room"
                 :id           "map"
                 :fill         "blue"
                 :fill-opacity "0.3"}]
      [hero hero-poss]
     ]]))


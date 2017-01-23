(ns dungeon-crawl.views
  (:require [re-frame.core :refer [subscribe] :as re-frame]
            [dungeon-crawl.consts :refer [sprites] :as consts]
            [dungeon-crawl.events]
            [debux.cs.core :refer-macros [clog dbg break]]
            ))

(def default-enemy   [{:x 100,
                       :y 100,
                       :icon "#vilain-thug",
                       :life 100,
                       :damage "d2"}])

(defn hero [[x y]]
  [:use { :x x, :y y, :xlinkHref "#hero"}])


(defn enemy [[x y]]
  [:use { :x x, :y y, :xlinkHref "#vilain-vampire"}])




(defn draw-sprite [{ [x y] :position, icon :icon}]
   [:use { :x  x,
          :y  y,
          :xlinkHref  icon}])


(defn draw-monsters []
  (let [enemies   @(subscribe [:monsters])  ]
     (map draw-sprite enemies)))



(defn draw-room []
  (let [hero-position (:position @(subscribe [:hero]))
        [width  height] (:dimensions  (:room @(subscribe [:room-viewed]))) ]
       [:svg {:width width,
             :height height,
             :style {:background-color "lightblue" }}
       [sprites]
         (into  [:g  [hero hero-position] ] (draw-monsters) )]))

(def pipa  (:dimensions (:room @(subscribe [:room-viewed]))))

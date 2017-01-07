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

(defn draw-monsters []
  (let [enemies (clog @(subscribe [:monsters]))]
    (map #([:use { :x (:x %),
                   :y (:y %),
                   :xlinkHref   (:icon %)}]) enemies)))



(defn draw-room []
  (let [hero-position (:position @(subscribe [:hero])) [width  height] (:dimensions  @(subscribe [:running-room])) ]
       [:svg {:width width,
             :height height,
             :style {:background-color "lightblue" }}
       [sprites]
       [:g [hero hero-position]]]))



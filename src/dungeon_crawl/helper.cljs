(ns dungeon-crawl.helper
   (:require
              [goog.math :as math]
              [re-frame.core :refer [dispatch subscribe] :as re-frame]
              [dungeon-crawl.consts :as consts]
              [taoensso.timbre :refer [spy]]))

(def min-dist 17)


(def directions [:up :up-left
                 :left :down-left
                 :down :down-right
                 :right :up-right])

(defn bind-keys []
  "This function binds the game controls to the keyboard"

  (js/Mousetrap.reset)
  (doseq
    [[x y] (map vector ["w" "d" "s" "a"]
                [:up :right :down :left])]
    (js/Mousetrap.bind x (fn []
                           (dispatch
                             [:move-hero y])))))



(defn distance
  "Euclidean distance between 2 points"
  [[x1 y1] [x2 y2]]
     (let [xdist (- x1 x2)
         ydist (- y1 y2)]
     (.sqrt js/Math (+ (* xdist xdist) (* ydist ydist)))))



(defn collision? [{H :position} {S :position}]
  "Check if the hero is to close to the sprite"
  (neg?  (- (distance H S) min-dist)))



(defn exchange-attacks [hero monster]
  "reduces the life of the hero and the engaged enemy by a
  random amount using there respective attacks."
    (let [hero-attack   (:damage  (:weapon  hero) )
                     monster-attack   ( :damage  monster  )]
            (vector (update-in hero [:life] #(- %  (monster-attack)))
                               (update-in monster [:life] #(- % (hero-attack))))))

(defn set-combat-outcome
  "returns the updated hero and enemy array after exchanging damage"
  [enemy-array hero]
  (let [ {[hostile & _]
          true non-hostile false}
         ;; group engaged enemies
            (group-by
                (partial collision? hero)
                         enemy-array)
         [wounded-hero wounded-enemy]
              (exchange-attacks  hero hostile)]
     (if  (< 0 (:life wounded-enemy))
       (vector wounded-hero (into [] (cons wounded-enemy non-hostile)))
                  (vector wounded-hero   non-hostile))))











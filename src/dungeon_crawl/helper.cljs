(ns dungeon-crawl.helper
   (:require
              [re-frame.core :refer [dispatch subscribe] :as re-frame]
              [dungeon-crawl.consts :as consts]
              [taoensso.timbre :refer [spy]]))




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



(defn sprite-neiborhood [[a b]]
  "outputs the squares adjanted in a given square"
  { :north [a  (- b 5)],
    :north-east [ (+ 5 a )     (- b 5)],
    :east [(+ 5 a )  b],
    :south-east [ (+ 5 a )  (+ 5  b)],
    :south [ a  (+ 5  b)],
    :south-west  [  (- a 5)   (+ 5  b)],
    :west [  (- a 5)  b],
    :north-west [  (- a 5)    (- b 5)]})



(defn collision? [hero sprite]
  "check if we are in the neiborhood of a sprite"
  (let [hero-neighborhood (vals  ( sprite-neiborhood  (hero :position)))]
     (some  #(= % (sprite :position) ) hero-neighborhood)))


(defn exchange-attacks [hero monster]
  "reduces the life of the hero and the engaged enemy by a
  random amount using there respective attacks."
  (let [ hero-attack   (:damage  (:weapon  hero) )
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
    (if  (> 0 (:life wounded-enemy))
      (vector wounded-hero  (cons wounded-enemy non-hostile))
      (vector wounded-hero   non-hostile))))











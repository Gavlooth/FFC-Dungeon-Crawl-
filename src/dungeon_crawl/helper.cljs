(ns dungeon-crawl.helper
   (:require  [debux.cs.core :refer-macros [clog dbg break]]
              [re-frame.core :refer [dispatch subscribe] :as re-frame]
              [dungeon-crawl.consts :as consts]))


(def directions [:up :up-left :left :down-left :down :down-right :right :up-right])

(defn bind-keys []
  "This function binds the game controls to the keyboard"
  (clog (let [[heigth width] ( :dimensions @(subscribe [:running-room]) )] (js/Mousetrap.reset)
          (doseq
            [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
            (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  heigth width])))))))



(defn sprite-neiborhood [[a b] [ heigth width]  ]
  "outputs the squares adjanted in a given square"
   {:north [a (max 0 (- b 5))],
    :north-east [(min (+ 5 a ) (- heigth 15))   (max 0 (- b 5))],
    :east [(min (+ 5 a ) (- heigth 15)) b],
    :south-east [(min (+ 5 a ) (- heigth 15)) (min (- width 15) (+ 5  b))],
    :south [ a (min (- width 15) (+ 5  b))],
    :south-west  [ (max 0 (- a 5))  (min (- width 15) (+ 5  b))],
    :west [ (max 0 (- a 5))  b],
    :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]})

(defn collision? [hero sprite room-dimensions]
  (let [hero-neighborhood    (dbg (vals  ( sprite-neiborhood  (hero :position) room-dimensions)))]
    (dbg (some  #(= %  (dbg  (sprite :position)) ) hero-neighborhood))))


 (def skata @(subscribe [:running-room]))


(defn exchange-attacks[hero monster]
  "reduces the life of the hero and the engaged enemy by a random amount based on there atacks."
  (let [ hero-attack ((hero :weapon) :damage)
         monster-attack ((monster :weapon) :damage)]
    (vector (update-in hero [:life] #(- %  (monster-attack)))
            (update-in monster [:life] #(- % (hero-attack))))))

(defn set-combat-outcome
  "returns the updated hero and enemy array after exchanging damage"
  [enemy-array]
  (let [ {[hostile & _]  true non-hostile false}
         (group-by is-neighbor? enemy-array)
         [wounded-hero wounded-enemy]
         (exchange-attacks hostile)]
    (vector wounded-hero  (cons wounded-enemy non-hostile))))




(def monster (first consts/default-enemy))

(def hero (consts/initial-state :hero))

(hero :position)

(monster :position)

(monster2 (assoc-in monster  :position   [120 120] ))

(collision?  hero monster [500 500])


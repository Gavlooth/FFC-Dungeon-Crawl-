(ns dungeon-crawl.helper
   (:require  [debux.cs.core :refer-macros [clog dbg break]]
              [re-frame.core :refer [dispatch subscribe]]))

(def directions [:up :up-left :left :down-left :down :down-right :right :up-right])

  (defn bind-keys []
    "This function binds the game controls to the keyboard"
    (clog (let [[haigth wadth] ( :dimensions @(subscribe [:running-room]) )] (js/Mousetrap.reset)
            (doseq
              [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
              (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  haigth wadth])))))))




(defn sprite-neiborhood [[a b]]
  "outputs the squares adjanted in a given square"
  (let [[haigth wadth ] @(subscribe [:running-room]) ]
    { :north [a (max 0 (- b 5))],
      :north-east [(min (+ 5 a ) (- haigth 15))   (max 0 (- b 5))],
      :east [(min (+ 5 a ) (- haigth 15)) b],
      :south-east [(min (+ 5 a ) (- haigth 15)) (min (- wadth 15) (+ 5  b))],
      :south [ a (min (- wadth 15) (+ 5  b))],
      :south-west  [ (max 0 (- a 5))  (min (- wadth 15) (+ 5  b))],
      :west [ (max 0 (- a 5))  b],
      :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]}))

(defn is-neighbor? [sprite]
  "check if a sprite  is adjanted to the hero"
      (let [hero-neighborhood    (vals  (sprite-neiborhood  ( @(subscribe [:hero]) :position)))]
               (some  #(= % sprite) hero-neighborhood)))





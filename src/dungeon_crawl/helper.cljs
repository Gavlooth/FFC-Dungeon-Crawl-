(ns dungeon-crawl.helper
   (:require  [debux.cs.core :refer-macros [clog dbg break]]
              [re-frame.core :refer [dispatch subscribe]]))

(def directions [:up :up-left :left :down-left :down :down-right :right :up-right])

  (defn bind-keys []
    "This function binds the game controls to the keyboard"
    (clog (let [[heigth weidth] ( :dimensions @(subscribe [:running-room]) )] (js/Mousetrap.reset)
            (doseq
              [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
              (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  heigth weidth])))))))




(defn sprite-neiborhood [[a b]]
  "outputs the squares adjanted in a given square"
  (let [[heigth weidth ] @(subscribe [:running-room]) ]
    { :north [a (max 0 (- b 5))],
      :north-east [(min (+ 5 a ) (- heigth 15))   (max 0 (- b 5))],
      :east [(min (+ 5 a ) (- heigth 15)) b],
      :south-east [(min (+ 5 a ) (- heigth 15)) (min (- weidth 15) (+ 5  b))],
      :south [ a (min (- weidth 15) (+ 5  b))],
      :south-west  [ (max 0 (- a 5))  (min (- weidth 15) (+ 5  b))],
      :west [ (max 0 (- a 5))  b],
      :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]}))

(defn is-neighbor? [sprite]
  "check if a sprite  is adjanted to the hero"
      (let [hero-neighborhood    (vals  (sprite-neiborhood  ( @(subscribe [:hero]) :position)))]
               (some  #(= % sprite) hero-neighborhood)))





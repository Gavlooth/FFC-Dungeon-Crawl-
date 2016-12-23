(ns dungeon-crawl.helper
   (:require  [debux.cs.core :refer-macros [clog dbg break]]
              [re-frame.core :refer [dispatch]]))

(def directions [:up :up-left :left :down-left :down :down-right :right :up-right])

  (defn bind-keys [width height]
    "This function binds the game controls to the keyboard"
    (js/Mousetrap.reset)
    (doseq
      [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
      (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  width height])))))




(defn sprite-neiborhood [ [a b] [width heigth ]]
  "outputs the squares adjanted in a given square"
  { :north [a (max 0 (- b 5))],
    :north-east [(min (+ 5 a ) (- width 15))   (max 0 (- b 5))],
    :east [(min (+ 5 a ) (- width 15)) b],
    :south-east [(min (+ 5 a ) (- width 15)) (min (- heigth 15) (+ 5  b))],
    :south [ a (min (- heigth 15) (+ 5  b))],
    :south-west  [ (max 0 (- a 5))  (min (- heigth 15) (+ 5  b))],
    :west [ (max 0 (- a 5))  b],
    :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]})

(defn is-neighbor? [sprite square dims]
  "check if a square is adjanted to a given square"
      (let [ squares   (vals  (sprite-neiborhood  square dims))]
               (some  #(= % sprite) squares)))







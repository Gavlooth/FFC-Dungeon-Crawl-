(ns dungeon-crawl.helper
   (:require  [taoensso.timbre :as t  ]
              [re-frame.core :refer [dispatch]]))

(def directions [:up :up-left :left :down-left :down :down-right :right :up-right])

  (defn bind-keys [width height]
    "This function binds the game controls to the keyboard"
    (js/Mousetrap.reset)
    (doseq
      [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
      (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  width height])))))

  (defn bind-diagonal-movement [width height]
      "Here we bind diagonal movement"
    (js/Mousetrap.bind  "c+v" (fn []  (dispatch  [:move-hero  :up-right  width height]))))

(t/info (map vector ["w" "d" "s" "a"]  [:up :right :down :left] [  ]))

;(t/spy :info)
(defn sprite-neiborhood [ [a b] [width heigth ]]
  { :north [a (max 0 (- b 5))],
    :north-east [(min (+ 5 a ) (- width 15))   (max 0 (- b 5))],
    :east [(min (+ 5 a ) (- width 15)) b],
    :south-east [(min (+ 5 a ) (- width 15)) (min (- heigth 15) (+ 5  b))],
    :south [ a (min (- heigth 15) (+ 5  b))],
    :south-west  [ (max 0 (- a 5))  (min (- heigth 15) (+ 5  b))],
    :west [ (max 0 (- a 5))  b],
    :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]})

(defn is-neighbor? [sprite square dims]
      (let [ squares   (vals  (sprite-neiborhood  square dims))]
               (some  #(= % sprite) squares)))







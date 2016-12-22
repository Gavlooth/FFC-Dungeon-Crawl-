(ns dungeon-crawl.helper

   (:require  [taoensso.timbre :as t  ]
              [re-frame.core :refer [dispatch]]))

(def directions  #{:up :up-left :left :down-left :down :down-right :right :up-right})


  (defn bind-keys [width height]
    (t/spy :info
           (doseq
             [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
             (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  width height]))))
           (doseq
             [[x y] (map vector ["w+d" "d+s" "s+a" "a+w"]  [:up-right :down-right :down-left :up-left] (repeatedly "keydown" ))]
             (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  width height]))))))



(defn sprite-neiborhood [ [a b] [width heigth ]]
  { :north [a (max 0 (- b 5))],
    :north-east [(min (+ 5 a ) (- width 15)) (max 0 (- b 5))],
    :east [(min (+ 5 a ) (- width 15)) b],
    :south-east [(min (+ 5 a ) (- width 15)) (min (- heigth 15) (+ 5  b))],
    :south [ a (min (- heigth 15) (+ 5  b))],
    :south-west  [ (max 0 (- a 5))  (min (- heigth 15) (+ 5  b))],
    :west [ (max 0 (- a 5))  b],
    :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]})

(defn is-neighbor? [sprite square dims]
      (let [ squares   (vals  (sprite-neiborhood  square dims))]
               (some  #(= % sprite) squares)))



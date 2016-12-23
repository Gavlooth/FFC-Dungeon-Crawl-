(ns dungeon-crawl.helper
   (:require  [taoensso.timbre :as t  ]
              [re-frame.core :refer [dispatch]]))

(def room-directions [:up :up-left :left :down-left :down :down-right :right :up-right])
(def control-keys ["w" "w+a" "a" "a+s" "s" "s+d" "d" "d+w"])



  (defn bind-keys [width height]
    "Bind the game controls to the keyboard"
    (let [set-keys (fn [x f z]     (if (nil? z)
                                     (t/spy :info  (js/Mousetrap.bind x  f))
                                       (js/Mousetrap.bind x f z)))]
      (doseq
        [[x y z] (map vector control-keys room-directions  (take 8 (cycle [nil "keydown"])) )]
           (set-keys x (fn []  (dispatch  [:move-hero y  width height])) z))))




  (map vector control-keys room-directions  (take 8 (cycle [nil "keydown"])) )

  (defn bind-bishop-movement [width height]
      "Here we bind diagonal movement"
    (js/Mousetrap.bind "d+w" (fn []  (dispatch  [:move-hero  :up-right  width height]))  "keydown" ))

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
  ( let [ squares   (vals  (sprite-neiborhood  square dims))]
    (some  #(= % sprite) squares)))


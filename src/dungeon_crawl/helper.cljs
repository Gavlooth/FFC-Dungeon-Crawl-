(ns dungeon-crawl.helper
   (:require  [debux.cs.core :refer-macros [clog dbg break]]
              [re-frame.core :refer [dispatch subscribe] :as re-frame]
              [dungeon-crawl.consts :as consts]))


(def directions [:up :up-left
                 :left :down-left
                 :down :down-right
                 :right :up-right])

(defn bind-keys []
  "This function binds the game controls to the keyboard"
  (clog (let [[heigth width]
              ( :dimensions @(subscribe [:running-room]) )]
          (js/Mousetrap.reset)
          (doseq
            [[x y] (map vector ["w" "d" "s" "a"]
                        [:up :right :down :left])]
            (js/Mousetrap.bind x (fn []
                                   (dispatch
                                          [:move-hero
                                             y
                                             heigth
                                             width])))))))



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
  (let [hero-neighborhood     (vals  ( sprite-neiborhood  (hero :position)))]
     (some  #(= %    (sprite :position) ) hero-neighborhood)))





(defn exchange-attacks [hero monster]
  "reduces the life of the hero and the engaged enemy by a random amount based on there atacks."
  (let [ hero-attack   (:damage  (:weapon  hero) )
         monster-attack   ( :damage  monster  )]
    (vector (update-in hero [:life] #(- %  (monster-attack)))
            (update-in monster [:life] #(- % (hero-attack))))))

(defn set-combat-outcome
  "returns the updated hero and enemy array after exchanging damage"
  [enemy-array hero]
  (let [ {[hostile & _]
          true non-hostile false} (dbg (group-by
                                         (partial collision? hero)
                                         enemy-array)) ;; group engaged enemies
         [wounded-hero wounded-enemy] (dbg
                                        (exchange-attacks
       ;;exchange attacks between hero and enemy
                                          hero hostile))]
     ;;return the hero and the enemy
    (vector wounded-hero  (cons wounded-enemy non-hostile))))



(defn sprite-neiborhood-restrained [[a b] [ heigth width]  ]
  "outputs the squares adjanted in a given square"
   {:north [a (max 0 (- b 5))],
    :north-east [(min (+ 5 a ) (- heigth 15))   (max 0 (- b 5))],
    :east [(min (+ 5 a ) (- heigth 15)) b],
    :south-east [(min (+ 5 a ) (- heigth 15)) (min (- width 15) (+ 5  b))],
    :south [ a (min (- width 15) (+ 5  b))],
    :south-west  [ (max 0 (- a 5))  (min (- width 15) (+ 5  b))],
    :west [ (max 0 (- a 5))  b],
    :north-west [ (max 0 (- a 5))   (max 0 (- b 5))]})







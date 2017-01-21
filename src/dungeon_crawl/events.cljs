(ns dungeon-crawl.events
   (:require [dungeon-crawl.consts :refer [initial-state default-enemy default-items]]
             [dungeon-crawl.helper :refer [ collision?]]
             [reagent.core :refer [render] :as reagent]
             [reagent.ratom :refer [reaction]]
             [cljsjs.mousetrap]
             [devtools.core :as devtools]
             [debux.cs.core :refer-macros [clog dbg break]]
             [re-frame.core :refer [reg-event-db
                                    subscribe
                                    dispatch
                                    dispatch-sync] :as re-frame]))

(enable-console-print!)



#_(reg-event-db
    :next-state
    (fn [db [_ &args]]
      (let [in-combat?  @(subscribe [:enemy-collision])
             near-item? @(subscribe [:item-collision])
             hero @(subscribe [:hero])
             enemies @(subscribe [:enemies])
             exits? nil
             new-level? nil]
        (if in-combat?
          (let [combat-outcome (helper/set-combat-outcome enemies hero)
                ]
                         ) ))))
           ;;This is the place we have to update


(reg-event-db
  :monster-to-heart
  (fn [db [_ &args]]
    (let [monsters @(subscribe [:monsters])]
      (update db :monsters
              (map
                (fn [x](assoc x :icon "#item-heart")
                     monsters))))))

(reg-event-db
  :initialize-db
  (fn [db _] (merge db initial-state)))

(reg-event-db
 :move-hero
 (fn [db [_ direction  width hight]]
   (cond
     (= :up direction) (update-in db [:hero :position]
                                  (fn [[a b]]  [a (max 0 (- b 5))]))
     (= :up-right direction) (update-in db [:hero :position]
                                        (fn [[a b]]
                                          [(min (+ 5 a )
                                               (- width 15))
                                                      (max 0 (- b 5))]))

     (= :right direction) (update-in db [:hero :position]
                                     (fn [[a b]]
                                       [(min (+ 5 a )
                                               (- width 15)) b]))
     (= :down-right direction) (update-in db [:hero :position]
                                          (fn [[a b]] [(min (+ 5 a)                                                                                 (- width 15))
                                                       (min (- hight 15)
                                                             (+ 5  b))]))

     (= :down direction) (update-in db [:hero :position]
                                    (fn [[a b]]
                                      [ a (min (- hight 15)
                                             (+ 5  b))]))
     (= :down-left direction) (update-in db [:hero :position]
                                         (fn [[a b]]
                                           [ (max 0 (- a 5))
                                             (min (- hight 15)
                                                  (+ 5  b))]))

     (= :left direction) (update-in db [:hero :position]
                                    (fn [[a b]]
                                      [ (max 0 (- a 5))  b]))

     (= :up-left direction) (update-in db [:hero :position]
                                       (fn [[a b]] [ (max 0 (- a 5))
                                         (max 0 (- b 5))]))

     :else ( println "direction not found"))))


(defn initialize-game [] (dispatch [:initialize-db]))


(ns dungeon-crawl.events
   (:require [dungeon-crawl.consts :refer [initial-state default-enemy default-items ]]
             [dungeon-crawl.helper :refer [ is-neighbor?]]
             [reagent.core :refer [render] :as reagent]
             [reagent.ratom :refer [reaction]]
             [cljsjs.mousetrap]
             [devtools.core :as devtools]
             [debux.cs.core :refer-macros [clog dbg break]]
             [re-frame.core :refer [reg-event-db subscribe dispatch dispatch-sync] :as re-frame]))

 (enable-console-print!)

 (defn attack-each-other [])


 (reg-event-db
   :next-state
   (fn [db [_ &args]]
     (let [in-combat?  @(subscribe [:enemy-collision])
            near-item? @(subscribe [:item-collision])
            exits? nil
            new-level? nil
            hero (subscribe [:hero] ) ]
       (if in-combat?
         (let  [enemy (filter  is-neighbor? @(subscribe [:monsters]))])))))




(reg-event-db
  :initialize-db
  (fn [db _] (merge db initial-state)))



 (reg-event-db
  :move-hero
  (fn [db [_ direction  width heigth]]
    (cond
      (= :up direction) (update-in db [:hero :position] (fn [[a b]]  [a (max 0 (- b 5))]))
      (= :up-right direction) (update-in db [:hero :position] (fn [[a b]]  [(min (+ 5 a ) (- width 15))
                                                                            (max 0 (- b 5))]))
      (= :right direction) (update-in db [:hero :position] (fn [[a b]] [(min (+ 5 a ) (- width 15)) b]))
      (= :down-right direction) (update-in db [:hero :position] (fn [[a b]] [(min (+ 5 a )
                                                                                  (- width 15))
                                                                              (min (- heigth 15)
                                                                                   (+ 5  b))]))

      (= :down direction) (update-in db [:hero :position] (fn [[a b]] [ a (min (- heigth 15)
                                                                               (+ 5  b))]))
      (= :down-left direction) (update-in db [:hero :position] (fn [[a b]] [ (max 0 (- a 5))
                                                                             (min (- heigth 15)
                                                                                  (+ 5  b))]))

      (= :left direction) (update-in db [:hero :position] (fn [[a b]] [ (max 0 (- a 5))  b]))

      (= :up-left direction) (update-in db [:hero :position] (fn [[a b]] [ (max 0 (- a 5))
                                                                           (max 0 (- b 5))]))
      :else ( println "direction not found"))))


(defn initialize-game [] (dispatch [:initialize-db]))


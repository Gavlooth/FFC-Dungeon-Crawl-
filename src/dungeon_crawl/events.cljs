(ns dungeon-crawl.events
  (:require [dungeon-crawl.consts :refer [initial-state default-enemy default-items]]
            [dungeon-crawl.helper :refer [ collision?] :as helper]
            [reagent.core :refer [render] :as reagent]
            [reagent.ratom :refer [reaction]]
            [cljsjs.mousetrap]
            [devtools.core :as devtools]
            [re-frame.core :refer [reg-event-db
                                   subscribe
                                   dispatch
                                   dispatch-sync] :as re-frame]
            [taoensso.timbre :refer [spy]]))

(enable-console-print!)









(reg-event-db
  :interact-with-items
 (fn [db _]
   (let [{{:keys [max-life life]} :hero } db ]
     (if-let  [heart (get-in db [:dungeon
                                 (:current-room db)
                                 :items
                                 :heart ]) ]

       (if (> max-life life)
         (update-in  (update-in db [:hero :life]
                                  (min max-life
                                       (+ life
                                          (:life-restore heart))))
                       [:dungeon
                        (:current-room db)
                        :items
                        :heart ] nil))
       (if-let [weapon (get-in db [:dungeon
                                 (:current-room db)
                                 :items
                                 :weapon ]) ]
         (update-in  (update-in db
                                [:hero :weapon]
                                weapon)
                       [:dungeon
                        (:current-room db)
                        :items
                        :heart ] nil))))))


(reg-event-db
  :initialize-db
  (fn [db _] (merge db initial-state)))

(reg-event-db
 :move-hero
 (fn [db [_ direction]]
   (let [ {:keys [ hero current-room dungeon-level dungeon]} db
          {:keys [dimensions items enemies exit] [width height] :dimensions }
           (get-in dungeon [current-room :room])
          in-combat?  (some #( collision? hero %) enemies)  ]
     (if in-combat?
       (let [ [wounded-hero wounded-enemy] (spy :info (helper/set-combat-outcome enemies hero))]
         (assoc
           (assoc-in
                       db
                       [:dungeon
                        current-room
                        :room :enemies] wounded-enemy)
           :hero wounded-hero))
       (cond
         (= :up direction) (update-in db [:hero :position]
                                      (fn [[a b]]  [a (max 0 (- b 5))]))


         (= :right direction)  (update-in db [:hero :position]
                                          (fn [[a b]]
                                            [(min (+ 5 a ) (- width 15)) b]))


         (= :down direction)  (update-in db [:hero :position]
                                         (fn [[a b]]
                                           [a (min   (- height 15)  (+ 5  b))]))


         (= :left direction) (update-in db [:hero :position]
                                        (fn [[a b]]
                                          [ (max 0 (- a 5))  b]))



         :else ( println "cannot follow direction"))))))


(defn initialize-game [] (dispatch [:initialize-db]))

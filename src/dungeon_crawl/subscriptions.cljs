(ns dungeon-crawl.subscriptions
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [reagent.ratom :refer [reaction]]
             [re-frame.core :refer [reg-sub-raw subscribe]]))


(reg-sub-raw
 :hero
 (fn
   [db _]
   (reaction (:hero @db))))

(reg-sub-raw
  :heart
  (fn
   [db _]
    (reaction (->  @db
                   :dungeon
                   (#(nth % (-> @db :current-room dec)))
                   :room
                   :items
                   :hearts))))

(reg-sub-raw
  :monsters
  (fn [db _]
    (reaction (->  @db
                   :dungeon
                   (#(nth % (-> @db :current-room dec)))
                   :room
                   :enemies))))



(reg-sub-raw
  :weapon
  (fn
   [db _]
    (reaction (->  @db
                   :dungeon
                   (#(nth % (-> @db :current-room dec)))
                   :room
                   :items
                   :weapon))))

 (reg-sub-raw
  :item-collision
  (fn
    [db _]
     (reaction (:item-collision @db))))

 (reg-sub-raw
   :enemy-collision
   (fn  [db _]
     (reaction (:enemy-collision @db))))

 (reg-sub-raw
   :running-room
   (fn [db _]
    (reaction (->  @db
                   :dungeon
                   (#(nth % (-> @db :current-room dec)))
                   :room))))



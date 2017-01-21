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
                   :heart))))

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
   :in-combat?
   (fn  [db _]
     (reaction (:in-combat? @db))))

 (reg-sub-raw
   :running-room
   (fn [db _]
    (reaction (->  @db
                   :dungeon
                   (#(nth % (-> @db :current-room dec)))
                   :room))))



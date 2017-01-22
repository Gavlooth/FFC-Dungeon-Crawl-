(ns dungeon-crawl.subscriptions
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [reagent.ratom :refer [reaction]]
             [re-frame.core :refer [reg-sub-raw subscribe] :as re-frame]))


(reg-sub-raw
 :hero
 (fn
   [db _]
   (reaction (:hero @db))))

(reg-sub-raw
  :heart
  (fn
   [db _]
    (reaction   (get-in @db [:dungeon (:current-room db) :room :items :heart]))))



(reg-sub-raw
  :monsters
  (fn [db _]
    (reaction (get-in @db [:dungeon (:current-room db) :room :enemies]) )))




(reg-sub-raw
  :weapon
  (fn
   [db _]
    (reaction [:dungeon (:current-room db) :room :items :weapon])))



 (reg-sub-raw
   :running-room
   (fn [db _]
      (reaction [:dungeon (:current-room db) :room])))



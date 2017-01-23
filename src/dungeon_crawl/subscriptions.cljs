(ns dungeon-crawl.subscriptions
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [reagent.ratom :refer [reaction]]
             [devtools.core :as devtools]
             [re-frame.core :refer [reg-sub-raw subscribe] :as re-frame]))

(enable-console-print!)
(reg-sub-raw
 :hero
 (fn
   [db _]
   (reaction (:hero @db))))

(reg-sub-raw
  :heart
  (fn
   [db _]
    (reaction   (get-in @db [:dungeon (:current-room @db) :room :items :heart]))))


(reg-sub-raw
  :room-viewed
  (fn [db _]
    (reaction (get-in @db [ :dungeon (:current-room @db)]))))


(reg-sub-raw
  :monsters
  (fn [db _]
    (reaction (get-in @db [:dungeon (:current-room @db) :room :enemies]) )))




(reg-sub-raw
  :weapon
  (fn
   [db _]
    (reaction (get-in @db  [:dungeon (:current-room @db) :room :items :weapon]))))




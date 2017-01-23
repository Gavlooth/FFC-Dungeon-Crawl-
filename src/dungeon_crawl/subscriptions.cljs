(ns dungeon-crawl.subscriptions
  (:require
             [reagent.ratom :refer [reaction]]
             [taoensso.timbre :refer [spy]]
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
    (reaction   (get-in @db [:dungeon
                             (:current-room @db)
                             :room
                             :items
                             :heart]))))


(reg-sub-raw
 :enemy-life
 (fn
   [db _]
   (reaction (:enemy-life @db))))

(reg-sub-raw
  :room-viewed
  (fn [db _]
    (reaction (get-in @db [ :dungeon
                            (:current-room @db)]))))


(reg-sub-raw
  :room-number
  (fn [db _]
    (reaction (:current-room @db))))

(reg-sub-raw
  :monsters
  (fn [db _]
    (reaction (get-in @db [:dungeon
                           (:current-room @db)
                           :room
                           :enemies]) )))

(reg-sub-raw
  :life
  ( fn [db _]
    (reaction (:life (:hero @db)))))


(reg-sub-raw
  :level
  ( fn [db _]
    (reaction (:character-level (:hero @db)))))

(reg-sub-raw
  :xp
  (fn
    [db _]
    (reaction (:experience-points (:hero @db)))))

(reg-sub-raw
  :dungeon-level
  (fn
   [db _]
    (reaction (:dungeon-level @db) )))

(reg-sub-raw
  :set-hero-life-bar
  (fn [db _]
    "Set the percentage of life in life-bar"
    (let [{ {:keys [max-life life]} :hero} @db]
      (reaction   (str  (* (quot life max-life) 100 ) "%")  ))))


(def sk @(subscribe [:set-hero-life-bar]))

(reg-sub-raw
  :weapon
  (fn
   [db _]
    (reaction (get-in @db
                      [:dungeon
                       (:current-room @db)
                       :room
                       :items
                       :weapon]))))

(reg-sub-raw
  :weapon-at-hand
  (fn [db _]
    (reaction  (get-in @db [:hero :weapon :name]))))


(reg-sub-raw
  :enemy-bar
  (fn [db _]
    (reaction (:enemy-bar @db))))

(def ss @(subscribe [:enemy-bar]))



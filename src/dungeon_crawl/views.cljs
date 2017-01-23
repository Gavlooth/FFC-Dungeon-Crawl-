(ns dungeon-crawl.views
  (:require [re-frame.core :refer [subscribe] :as re-frame]
            [dungeon-crawl.consts :refer [sprites] :as consts]
            [dungeon-crawl.events]

            ))

(def default-enemy   [{:x 100,
                       :y 100,
                       :icon "#vilain-thug",
                       :life 100,
                       :damage "d2"}])

(defn hero [[x y]]
  [:use { :x x, :y y, :xlinkHref "#hero"}])


(defn enemy [[x y]]
  [:use { :x x, :y y, :xlinkHref "#vilain-vampire"}])




(defn draw-sprite [{ [x y] :position, icon :icon}]
   [:use { :x  x,
          :y  y,
          :xlinkHref  icon}])


(defn draw-monsters []
  (let [enemies   @(subscribe [:monsters])  ]
     (map draw-sprite enemies)))

(defn draw-game-status-bar [life level
                            dungeon-level
                            room xp
                            enemy-life
                            weapon
                            hero-bar
                            enemy-bar]
  [:div {:id "status-bar"}
   [:div
    {:id "dungeon-level"} [:p (str  "Depth: " dungeon-level)]]
   [:div
    {:id "room"} [:p (str  "Room: " room)]]
   [:div
    {:id "level"} [:p  (str "Level: " level)]]
   [:div
    {:id "xp"} [:p (str "Xp: " xp)]]
   [:div
    {:id "weapon"} [:p (str "Weapon: " weapon)]]
   [:div
    {:id "hero-life"} [:p  "Hero:"]]
  [:div {:class "szlider"}
 [:div {:class "szliderbar"  :style {:width  hero-bar}} ]
 [:div {:class "szazalek"} ]]
   [:div
    {:id "enemy-life"} [:p "Enemy: "]]
   [:div {:class "szlider"}
 [:div {:class "szliderbar"  :style {:width  enemy-bar}} ]
 [:div {:class "szazalek"} ]]])

(defn draw-room []
  (let [hero-position (:position @(subscribe [:hero]))
        [width  height] (:dimensions  (:room @(subscribe [:room-viewed])))]
    [:div {:id "game-window" }
     [draw-game-status-bar @(subscribe [:life])
      @(subscribe [:level])
      @(subscribe [:dungeon-level])
      @(subscribe [:room-number])
      @(subscribe [:xp])
      @(subscribe [:enemy-life])
      @(subscribe [:weapon-at-hand])
      @(subscribe [:set-hero-life-bar])
      @(subscribe [:enemy-bar])]

     [:svg {:width width,
            :height height,
            :style {:background-color "lightblue" }}
      [sprites]
      (into  [:g  [hero hero-position] ] (draw-monsters) )]]))



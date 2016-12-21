(ns dungeon-crawl.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :refer [render] :as reagent]
            [dungeon-crawl.views :as views]
            [taoensso.timbre :as timbre]
            [devtools.core :as devtools]
            [re-frame.core :refer [reg-event-db register-sub subscribe dispatch dispatch-sync] :as re-frame]
            [goog.events :as events]
            [cljsjs.mousetrap]
            [dungeon-crawl.events :refer [initialize-game]]))

(timbre/info  (enable-console-print!))





 (defn bind-keys [width height]
      (doseq
          [[x y] (map vector ["w" "d" "s" "a"]  [:up :right :down :left])]
        (js/Mousetrap.bind x (fn [] (dispatch  [:move-hero y  width height])))))





 (defn mount-root []
   (let [[width height]  (views/random-room-dimensions)]
    (initialize-game)
     (bind-keys width height)
     (render   [views/generate-room width height]  (.getElementById js/document "app"))))

(mount-root)









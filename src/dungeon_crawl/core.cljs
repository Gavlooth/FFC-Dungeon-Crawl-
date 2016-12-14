(ns dungeon-crawl.core
  (:require [reagent.core :refer[render] :as r]
            [dungeon-crawl.views :as views]
            [re-frame.core :refer [reg-event-db dispatch-sync ] :as re-frame]))
(enable-console-print!)





(def default-enemy   [{:x 100,
                       :y 100,
                       :icon "#vilain-thug",
                       :life 100,
                       :damage "d2"}])

(def default-item-med [{:x 130,
                        :y 150,
                        :icon "#item-heart"
                        :life-restore 20}])

(def initial-state {:dungeon [{:room       {:dimensions [300 500],
                                             :items      default-item-med,
                                             :enemies    default-enemy,
                                             :exit       [300 (+ 20 (rand-int 460))]}}],
                    :current-room 1,
                    :dungeon-level 1,
                    :hero    {:life 100,
                              :weapon {:name "bare hands", :damage "d4"},
                              :character-level  1,
                              :experience-points 100,
                              :position [10 500]}})





(defn mount-root []
  (render   [views/generate-room [ 50 50]]  (.getElementById js/document "app")))





(reg-event-db
  :initialize
  (fn [db _] (merge db initial-state)))

(defn initialize-game [] (dispatch-sync [:initialize]))
(mount-root)




#_((ns your-project.namespace
  (:require [dirac.runtime]))

#_(dirac.runtime/install!))

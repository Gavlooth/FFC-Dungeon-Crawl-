(ns dungeon-crawl.core
  (:require-macros [reagent.ratom :refer [reaction]] )
  (:require [reagent.core :refer [render] :as reagent]
            [dungeon-crawl.views :as views]
            [dungeon-crawl.subscriptions]
            [devtools.core :as devtools]
            [re-frame.core :refer [reg-event-db register-sub subscribe dispatch dispatch-sync] :as re-frame]
            [goog.events :as events]
            [cljsjs.mousetrap]
            [debux.cs.core :refer-macros [clog dbg break]]
            [dungeon-crawl.helper :refer [bind-keys]]
            [dungeon-crawl.events :refer [initialize-game]]))
  (enable-console-print!)

 (defn mount-root []
  (initialize-game)
  (let [[width heigth] (:dimensions @(subscribe [:running-room])) ]
    (js/Mousetrap.reset)
     (bind-keys)
    (clog (render   [views/generate-room width heigth]  (.getElementById js/document "app")))))

  (mount-root)









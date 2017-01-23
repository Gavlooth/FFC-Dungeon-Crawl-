(ns dungeon-crawl.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :refer [render] :as reagent]
            [dungeon-crawl.views :as views :refer [draw-room]]
            [dungeon-crawl.subscriptions]
            [devtools.core :as devtools]
            [re-frame.core :refer [reg-event-db
                                   register-sub
                                   subscribe
                                   dispatch
                                   dispatch-sync] :as re-frame]
            [goog.events :as events]
            [cljsjs.mousetrap]
            [debux.cs.core :refer-macros [clog dbg break]]
            [dungeon-crawl.helper :refer [bind-keys]]
          [dungeon-crawl.events :refer [initialize-game]]))
(enable-console-print!)

(defn run []
  (do
    (initialize-game)
    (js/Mousetrap.reset)
    (bind-keys)
    (render   [draw-room]
              (.getElementById js/document "app"))))

 (run)









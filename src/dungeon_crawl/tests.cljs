(ns dungeon-crawl.tests
  (:require  [taoensso.timbre :refer [spy]]
             [devtools.core :as devtools]
             [dungeon-crawl.helper :as helper]
             [dungeon-crawl.consts :as consts :refer [initial-state default-enemy]]
             [cljs.test :refer-macros [deftest is testing run-tests]]))
(enable-console-print!)




(def hero ( :hero initial-state ))












(def monster (assoc (first default-enemy) :position [105 100]))




 (helper/exchange-attacks hero monster)



(run-tests 'dungeon-crawl.tests)


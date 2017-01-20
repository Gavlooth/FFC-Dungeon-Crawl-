(ns dungeon-crawl.tests
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [devtools.core :as devtools]
             [dungeon-crawl.helper :as helper]
             [dungeon-crawl.consts :as consts :refer [initial-state default-enemy]]
             [cljs.test :refer-macros [deftest is testing run-tests]]))
(enable-console-print!)




(def hero (consts/initial-state :hero))












(def monster (assoc (first default-enemy) :position [105 100]))




 (helper/exchange-attacks hero monster)



(run-tests 'dungeon-crawl.tests)


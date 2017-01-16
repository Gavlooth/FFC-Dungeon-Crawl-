(ns dungeon-crawl.tests
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [devtools.core :as devtools]
             [dungeon-crawl.helper :as helper]
             [dungeon-crawl.consts :as consts :refer [initial-state default-enemy]]
             [cljs.test :refer-macros [deftest is testing run-tests]]))
(enable-console-print!)


(def monster (assoc  (first consts/default-enemy) :position  [100 125] ))

(def hero (consts/initial-state :hero))





(is (= true  (helper/collision? monster hero)))

(monster :position)
;;tests for helper functions




(def monster (assoc (first default-enemy) :position [105 100]))

(def hero (initial-state :hero))


(dbg (helper/exchange-attacks herro monster))



(run-tests 'dungeon-crawl.tests)


(ns dungeon-crawl.tests
  (:require  [debux.cs.core :refer-macros [clog dbg break]]
             [devtools.core :as devtools]
             [dungeon-crawl.helper :as helper]
             [dungeon-crawl.consts :as consts :refer [initial-state default-enemy]]
             [cljs.test :refer-macros [deftest is testing run-tests]]))
(enable-console-print!)


(def monster (first consts/default-enemy))

(def hero (consts/initial-state :hero))



(def monster (first consts/default-enemy))

(def hero (consts/initial-state :hero))


(is (= true  (helper/collision? monster hero)))

(monster :position)
;;tests for helper functions
(is (= 4 (+ 2 2)))



(def monster (first default-enemy))

(def hero (initial-state :hero))


(helper/collision2? monster hero)



(run-tests 'dungeon-crawl.test)


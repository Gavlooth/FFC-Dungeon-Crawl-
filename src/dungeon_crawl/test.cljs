(ns dungeon-crawl.test
  (:require  [taoensso.timbre :as t]
             [devtools.core :as devtools]
             [dungeon-crawl.helper :as helper]
             [cljs.test :refer-macros [deftest is testing run-tests]]))
(enable-console-print!)



(deftest test-neighbors
    (is (= true  (helper/is-neighbor? [10 10] [10 5]  [500 500])))
  (is (nil? (t/spy :info (helper/is-neighbor? [10 10] [10 25]  [500 500]))))
  )

(t/spy :info (helper/sprite-neiborhood [10 10]  [500 500]))

(run-tests 'dungeon-crawl.test)


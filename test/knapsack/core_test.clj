(ns knapsack.core-test
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [knapsack.core :refer :all]))

#_(facts "An end-to-end test"
         (let [menu {:soup 1, :steak 2}
               target 3]
           (solve menu target) => #{[:soup :soup :soup] [:steak :soup]}))

(fact "initial-collections returns each item from menu"
      (generate-initial-collections {:soup 1, :steak 2}) => #{[:soup] [:steak]})

(facts "no-orders-can-accept-additional-item?"
       (let [menu {:soup 1, :steak 2}
             target 3
             orders #{[:soup :soup :soup]
                      [:steak :soup]}]
         (fact
           (no-orders-can-accept-additional-item?
             {:soup 1 :steak 2}
             3
             #{[:soup :soup :soup]
               [:steak :soup]}) => true)))

(fact "over-target returns true when the order is over the target price"
      (over-target? {:steak 2} 3 [:steak :steak]) => true
      (over-target? {:steak 2} 3 [:steak]) => false)

(facts "generate-candidate-orders"
       (let [menu {:a 1, :b 2, :c 3}
             order [:a]]
         (fact
           (sort (generate-candidate-orders menu order)) =>
           (sort [[:a :a] [:a :b] [:a :c]]))))

(facts "generate-all-candidate-orders"
       (fact "it generates the next possible orders for all orders"
             (let [menu {:a 1, :b 2, :c 3}
                   orders #{[:a] [:b]}]
               (fact
                 (generate-all-candidate-orders menu orders) => #{[:a :a]
                                                                  [:a :b]
                                                                  [:a :c]
                                                                  [:b :a]
                                                                  [:b :b]
                                                                  [:b :c]}))))

(facts "matches-target?"
       (let [menu {:a 1, :b 2, :c 3}
             target 3]
         (matches-target? menu target [:a]) => false
         (matches-target? menu target [:a :b]) => true
         (matches-target? menu target [:c]) => true
         (matches-target? menu target [:a :a :a]) => true))

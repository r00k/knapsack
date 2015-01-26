(ns knapsack.core-test
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [knapsack.core :refer :all]))

#_(deftest end-to-end
    (testing "A simple end-to-end example"
      (let [menu {:soup 1, :steak 2}
            target 3]
        (is (= #{[:soup :soup :soup]
                 [:steak :soup]}
               (solve menu target))))))

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

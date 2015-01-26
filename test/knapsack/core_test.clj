(ns knapsack.core-test
  (:require [clojure.test :refer :all]
            [knapsack.core :refer :all]))

(deftest end-to-end
  (testing "A simple end-to-end example"
    (let [menu {:soup 1, :steak 2}
          target 3]
      #_(is (= #{[:soup :soup :soup]
                 [:steak :soup]}
               (solve menu target))))))

(deftest initial-collections
  (is (= #{[:soup] [:steak]}
         (generate-initial-collections {:soup 1, :steak 2}))))

#_(deftest no-orders-can-accept-additional-item?-test
    (let [menu {:soup 1, :steak 2}
          target 3
          orders #{[:soup :soup :soup]
                   [:steak :soup]}
          order-generator (fn [_ _] #{[:soup :soup :soup :soup]
                                      [:soup :soup :soup :steak]
                                      [:steak :soup :soup]
                                      [:steak :soup :steak]})]
      (is (= true
             (no-orders-can-accept-additional-item? menu target orders order-generator)))))

(deftest over-target-test
  (is (= true
         (over-target? {:steak 2} 3 [:steak :steak])))
  (is (= false
         (over-target? {:steak 2} 3 [:steak])))) 


(deftest generate-candidate-orders-test
  (let [menu {:a 1, :b 2, :c 3}
        order [:a]]
    (is (= #{[:a :a] [:a :b] [:a :c]}
           (generate-candidate-orders menu order)))))

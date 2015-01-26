(ns knapsack.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

; given a set of menu items, return all combinations of items
; that add up to a given total (repeats of items is allowed)

; algorithm:
;   create a collection of single-item collections for each item
;   until none of the collections can accept another item
;     for each collection
;       add each item

(defn- total-cost [menu order]
  (let [amounts (map menu order)]
    (reduce + amounts)))

(defn matches-target? [menu target order]
  (= (total-cost menu order)
     target))

(defn over-target? [menu target order]
  (> (total-cost menu order) target))

(defn- generate-sorted-candidate-orders
  [menu order]
  (map (fn [item] (sort (conj order item)))
       (keys menu)))

(defn generate-all-candidate-orders
  [menu orders]
  (set (mapcat (partial generate-sorted-candidate-orders menu)
               orders)))

(defn no-orders-can-accept-additional-item?
  [menu target orders]
  (let [new-orders (generate-all-candidate-orders menu orders)]
    (every? (partial over-target? menu target) new-orders)))

(defn generate-initial-collections [menu]
  (set (map vector (keys menu))))

(defn solve-helper [menu target orders]
  (loop [orders orders matched-orders #{}]
    (if (no-orders-can-accept-additional-item? menu target orders)
      (into matched-orders (filter (partial matches-target? menu target)
                                   orders))
      (recur (generate-all-candidate-orders menu orders)
             (into matched-orders
                   (filter (partial matches-target? menu target) orders))))))

(defn solve [menu target]
  ; generate initial collections
  ; until none of the collections can accept another item
  ;   for every order
  ;     combine every item
  ;   reject orders that are over target
  ; filter orders that match target
  (let [initial-collections (generate-initial-collections menu)]
    (solve-helper menu target initial-collections)))

(ns tylerw.advent-of-code-2021.day-11
  (:require [clojure.core.matrix :as m]
            [medley.core :as md]
            [net.cgrand.xforms :as x]
            [tylerw.advent-of-code-2021.utils :as u]))

(def FLASH-THRESHOLD 9)

(def input
  (let [xf (comp (map seq)
                 (map (fn [coll] (map #(Character/digit % 10) coll))))]
    (into [] xf (u/day-input-source 11))))

(defn flashes?
  [v]
  (> v FLASH-THRESHOLD))

(defn get-flash-indices
  [grid]
  (let [source (->> grid
                    (m/emap (fn [v] (if (flashes? v) v 0)))
                    m/non-zero-indices
                    md/indexed)
        xf (mapcat (fn [[i vs]] (for [v vs] [i v])))]
    (sequence xf source)))

(defn neighbors
  [grid index]
  (let [[x y] index
        [X Y] (m/shape grid)]
    (for [dx (range -1 2)
          dy (range -1 2)
          :let [new-x (+ x dx)
                new-y (+ y dy)]
          :when (not= 0 dx dy)
          :when (and (< -1 new-x X) (< -1 new-y Y))]
      [new-x new-y])))

(defn propagate-flash
  [grid [x y :as flasher]]
  (let [source (neighbors grid flasher)
        f (fn [grid [x y]]
            (let [v (m/mget grid x y)]
              (if (zero? v)
                grid
                (doto grid (m/mset! x y (inc v))))))
        init (doto grid (m/mset! x y 0))]
    (reduce f init source)))

(defn step
  [grid]
  (loop [grid (m/add! grid 1)]
    (if-let [flashers (-> grid get-flash-indices seq)]
      (recur (reduce propagate-flash grid flashers))
      grid)))

(defn t1
  [input]
  (let [source (iterate step (m/mutable input))
        xf (comp (take 101) (mapcat m/eseq) (filter zero?) x/count)]
    (x/some xf source)))

(defn t2
  [input]
  (let [source (iterate step (m/mutable input))
        xf (comp (take-while #(pos? (m/esum %))) x/count)]
    (x/some xf source)))

(defn -main
  [& _]
  (println ((juxt t1 t2) input)))

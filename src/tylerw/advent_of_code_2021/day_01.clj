(ns tylerw.advent-of-code-2021.day-01
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [net.cgrand.xforms :as x]))

(def input (into [] (map parse-long) (u/day-input-source 1)))

(defn t1
  [input]
  (let [xf (comp (x/partition 2 1)
                 (keep #(when (apply < %) 1)))]
    (transduce xf + input)))

(defn t2
  [input]
  (let [xf (comp (x/window 3 + -) (drop 2))]
    (t1 (sequence xf input))))

(println (t1 input) (t2 input))

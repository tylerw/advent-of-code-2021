(ns tylerw.advent-of-code-2021.day-07
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [net.cgrand.xforms :as x]
            [clojure.java.math :as math]))

(def sample-input [16,1,2,0,4,2,7,1,2,14])
(comment ((juxt t1 t2) sample-input))

(def input (-> (u/day-input-resource 7) slurp u/read-string-as-vec))

(defn triangle
  [n]
  (/ (* n (inc n)) 2))

(defn t0
  [additional-xf input]
  (let [[start end] (x/transjuxt [x/min x/max] input)
        f (fn [pos] (transduce
                      (comp (map #(math/abs (- pos %))) additional-xf)
                      +
                      input))]
    (x/some (comp (map f) x/min) (range start (inc end)))))

(def t1 (partial t0 identity))
(def t2 (partial t0 (map triangle)))

(defn -main
  [& _]
  (println ((juxt t1 t2) input)))

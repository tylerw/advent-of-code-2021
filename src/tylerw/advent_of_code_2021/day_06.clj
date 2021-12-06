(ns tylerw.advent-of-code-2021.day-06
  (:require [tylerw.advent-of-code-2021.utils :as u]))

(def DUPE-PERIOD 7)
(def NEWBORN-STAGE 8)

(def input (-> (u/day-input-resource 6) slurp u/read-string-as-vec))

(let [count-fish (memoize
                   (fn [f stage days]
                     (let [source (range (- days stage) 0 (- DUPE-PERIOD))
                           xf (map #(f f (inc NEWBORN-STAGE) %))]
                       (transduce xf + 1 source))))]
  (def count-fish (partial count-fish count-fish)))

(defn model
  [days input]
  (transduce (map #(count-fish % days)) + input))

(defn -main
  [& _]
  (println (map #(model % input) [80 256])))

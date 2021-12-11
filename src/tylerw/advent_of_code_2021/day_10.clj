(ns tylerw.advent-of-code-2021.day-10
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [net.cgrand.xforms :as x]))

(def input (into [] (u/day-input-source 10)))
(def pair (into {} (x/partition 2) "()[]{}<>"))

(defn parse-line
  [line]
  (-> (fn [acc c]
        (if-let [close (pair c)]
          (update acc :stack conj close)
          (if (= c (-> acc :stack first))
            (update acc :stack rest)
            (reduced {:corrupt c}))))
      (reduce {:stack (list)} line)))

(def parsed-input (eduction (map parse-line) input))

(defn t1
  [parsed-input]
  (let [score (zipmap (vals pair) [3 57 1197 25137])
        xf (comp (keep :corrupt) (map score))]
    (transduce xf + parsed-input)))

(defn t2
  [parsed-input]
  (let [delim-score (zipmap (vals pair) (range 1 5))
        score (fn [coll]
                (reduce (fn [acc c] (+ (* 5 acc) c)) (map delim-score coll)))
        results (into [] (comp (keep :stack) (map score)) parsed-input)]
    (nth (sort results) (quot (count results) 2))))

(defn -main
  [& _]
  (println ((juxt t1 t2) parsed-input)))

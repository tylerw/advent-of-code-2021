(ns tylerw.advent-of-code-2021.day-09
  (:require [clojure.core.matrix :as m]
            [net.cgrand.xforms :as x]
            [tylerw.advent-of-code-2021.utils.matrix :as um]))

(def input (-> (um/day-input-intmatrix 9) (um/wrap-matrix 9) m/mutable))

(defn low-point?
  "Is the [1,1] element of a 3x3 matrix less than all other elements?"
  [matrix]
  (let [inner (m/mget matrix 1 1)]
    (when (= 1 (m/esum (m/lt matrix (inc inner))))
      inner)))

(defn low-points
  [input]
  (eduction (filter (comp low-point? second))
            (um/inner-3x3-submatrices input)))

(defn basin-size
  [matrix coord]
  (loop [size 0
         queue (list coord)]
    (if (seq queue)
      (let [[i j] (first queue)]
        (if (= 9 (m/mget matrix i j))
          (recur size (pop queue))
          (do
            (m/mset! matrix i j 9)
            (recur
              (inc size)
              (conj (pop queue)
                    [(dec i) j] [(inc i) j]
                    [i (dec j)] [i (inc j)])))))
      size)))


(let [coord-and-subm (low-points input)]
  (defn t1
    []
    (let [xf (map (comp inc #(m/mget % 1 1) second))]
      (transduce xf + coord-and-subm)))

  (defn t2
    []
    (let [xf (comp (map (comp (partial basin-size input) first))
                   (x/sort >)
                   (take 3))]
      (transduce xf * coord-and-subm))))

(defn -main
  [& _]
  (println ((juxt t1 t2))))

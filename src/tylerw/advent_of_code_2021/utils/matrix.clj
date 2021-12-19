(ns tylerw.advent-of-code-2021.utils.matrix
  (:require [clojure.core.matrix :as m]
            #_[net.cgrand.xforms.io :as xio]
            [tylerw.advent-of-code-2021.utils :as u]))

(defn day-input-intmatrix
  "Read a block of numbers (from a day's input) as a matrix."
  [n]
  (let [xf (comp (map seq)
                 (map (fn [coll] (map #(Character/digit % 10) coll))))]
    (-> (into [] xf (u/day-input-source n))
        m/matrix)))

(defn wrap-matrix
  "Surround the matrix in a 'border' of sentinels. That is, add rows before the
  first and after the last rows and add columns before the first and after the
  last columns."
  [matrix sentinel]
  (let [n (m/dimension-count matrix 1)
        blank-row (vec (repeat (+ n 2) sentinel))
        step-1 (reduce (fn [mat v]
                         (conj mat
                               (vec (concat [sentinel] v [sentinel]))))
                       [blank-row]
                       matrix)]
    (conj step-1 blank-row)))

(defn inner-3x3-submatrices
  "Return a sequence of 3x3 submatrices for each inner coordinate and their
  neighbords. Every element returned will look like [[i j] submatrix]."
  [matrix]
  (let [[max-i max-j] (map dec (m/shape matrix))
        outer-edge? (fn [[i j]]
                      (or (zero? i) (zero? j) (= max-i i) (= max-j j)))
        subm3x3 (fn [[i j]] (m/submatrix matrix [[(dec i) 3] [(dec j) 3]]))
        xf (comp (remove outer-edge?) (map (juxt identity subm3x3)))]
    (sequence xf (m/index-seq matrix))))

(ns tylerw.advent-of-code-2021.day-05
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [net.cgrand.xforms :as x]
            [clojure.java.math :as math]
            [clojure.core.matrix :as m]))

(def input (-> (into [] 
                 (map (fn [line]
                        (let [[x1 y1 _ x2 y2] (u/read-string-as-vec line)]
                          [[x1 y1] [x2 y2]])))
                 (u/day-input-source 5))
               m/matrix))

(defn chessboard-dist
  ([u v] (chessboard-dist (m/sub v u)))
  ([v] (apply max (m/emap math/abs v))))

(defn line [[start end]]
  (let [len (chessboard-dist start end)
        dir (m/div (m/sub end start) len)
        xf (map #(m/add start (m/mul % dir)))]
    (sequence xf (range (inc len)))))

(defn t0
  [additional-xf coords]
  (let [xf (comp additional-xf
                 (mapcat line)
                 (x/by-key identity x/count)  ;a.k.a., frequencies
                 (map second)
                 (keep #(when (< 1 %) %))
                 x/count)]
    (first (sequence xf coords))))

(defn t1
  [input]
  (let [h? (fn [[[x1 _] [x2 _]]] (= x1 x2))
        v? (fn [[[_ y1] [_ y2]]] (= y1 y2))]
    (t0 (filter #(or (h? %) (v? %))) input)))

(defn t2
  [input]
  (t0 identity input))

(defn -main
  [& _]
  (println ((juxt t1 t2) input)))

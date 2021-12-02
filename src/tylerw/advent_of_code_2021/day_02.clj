(ns tylerw.advent-of-code-2021.day-02
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [clojure.edn :as edn]))

(def input (->> (u/day-input-source 2)
                (into [] (map (comp edn/read-string #(str "{:" % "}"))))))

(defn t1
  [input]
  (let [combined (reduce (fn [acc x] (merge-with + acc x)) input)
        {:keys [forward up down]} combined]
    (* forward (- down up))))

(defn t2
  [input]
  (let [f (fn [acc action]
            (let [[direction x] (-> action seq first)]
              (case direction
                :down    (update acc :aim + x)
                :up      (update acc :aim - x)
                :forward (-> acc
                             (update :horiz + x)
                             (update :depth + (* (:aim acc) x))))))
        init (zipmap [:horiz :depth :aim] (repeat 0))
        {:keys [horiz depth]} (reduce f init input)]
    (* horiz depth)))

(println (t1 input) (t2 input))

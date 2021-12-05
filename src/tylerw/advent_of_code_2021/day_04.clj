(ns tylerw.advent-of-code-2021.day-04
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [net.cgrand.xforms :as x]
            [clojure.string :as str]
            [flatland.ordered.set :refer [ordered-set]]
            [clojure.core.matrix :as m]))

(def edx (u/day-input-source 4))
(def nums (->> edx (into [] (comp (take 1) (map u/read-string-as-vec))) first))
(def boards (->> edx (into [] (comp (drop 1)
                                   (filter (complement str/blank?))
                                   (map u/read-string-as-vec)
                                   (x/partition 5)))))

(defn bingo?
  [board called]
  (some #(every? called %) (concat (m/rows board) (m/columns board))))

(defn play
  [nums board]
  (let [f (fn [called n]
            (if (bingo? board called)
              (reduced called)
              (conj called n)))]
    (reduce f (ordered-set) nums)))

(defn score
  [board called]
  (-> (transduce (comp cat (remove called)) + board)
      (* (last called))))

(defn play-all
  [nums boards]
  (let [xf (map #(let [c (play nums %)] [(count c) (score % c)]))
        wins (->> boards (into [] xf) (sort-by first))]
    ((juxt (comp second first) (comp second last)) wins)))

(defn -main
  [& _]
  (let [[t1 t2 :as tasks] (play-all nums boards)]
    (comment t1 t2)
    (println tasks)))

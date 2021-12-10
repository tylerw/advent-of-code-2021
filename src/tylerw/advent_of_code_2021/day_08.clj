(ns tylerw.advent-of-code-2021.day-08
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [medley.core :refer [indexed]]
            [net.cgrand.xforms :as x]))

(def sample-input "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
(let [raw-input (eduction
                  (comp (mapcat #(re-seq #"[a-g]+" %))
                        (map set)
                        (x/partition 14))
                  (u/day-input-source 8))]
  (def outputs (into [] (map #(drop 10 %)) raw-input))
  (def signal-patterns (into [] (map #(take 10 %)) raw-input)))

(defn unique? [signal]
  (contains? #{2 3 4 7} (count signal)))

(defn t1
  [input]
  (x/some (comp cat (filter unique?) x/count) input))

(def digit->segments
  ["abcefg" "cf" "acdeg" "acdfg" "bcdf" "adbfg" "abdefg" "acf" "abcdefg"
   "abcdfg"])

(defn signals->freqs
  [signals]
  (into {} (comp cat (x/by-key identity x/count)) signals))

(defn gen-decoder
  [signals]
  (let [freqs (signals->freqs signals)]
    (fn [k] (->> k (map freqs) sort))))

(def freqs->digit
  (let [f (gen-decoder digit->segments)
        xf (comp (indexed) (map (fn [[i v]] [(f v) i])))]
    (into {} xf digit->segments)))

(defn decode
  [[signal-pattern output]]
  (let [f (gen-decoder signal-pattern)
        xf (map (fn [k] [k (-> k f freqs->digit)]))
        decoder (into {} xf signal-pattern)]
    (transduce (map decoder) str "" output)))

(defn t2
  [input]
  (let [xf (map (comp parse-long decode))]
    (transduce xf + input)))

(defn -main
  [& _]
  (let [t1-ans (t1 outputs)
        t2-ans (t2 (map vector signal-patterns outputs))]
    (println [t1-ans t2-ans])))

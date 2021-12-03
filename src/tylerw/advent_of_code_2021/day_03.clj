(ns tylerw.advent-of-code-2021.day-03
  (:require [tylerw.advent-of-code-2021.utils :as u]
            [clojure.string :as str]
            [clojure.core.matrix :as m]))

(def input (let [xf (map (fn [s] (map #(Character/digit % 2) s)))]
             (->> (u/day-input-source 3)
                  (into [] xf)
                  m/matrix)))

(def parse-bin #(u/parse-int % 2))

(defn t1
  [input]
  (let [half (quot (count input) 2)
        xf (map (comp #(if (< % half) 0 1) m/esum))
        bits (transduce xf str "" (m/columns input))
        gamma-rate (parse-bin bits)
        epsilon-rate (parse-bin (str/escape bits {\0 \1 \1 \0}))]
    (* gamma-rate epsilon-rate)))

(defn t2'
  [input cmp]
  (reduce (fn [acc i]
            (let [ones (m/esum (m/get-column acc i))
                  zeros (- (count acc) ones)
                  bit (if (cmp zeros ones) 0 1)
                  matches (filter #(= (nth % i) bit) acc)]
              (if (= (count matches) 1) (reduced (first matches)) matches)))
          input
          (range (m/column-count input))))

(defn t2
  [input]
  (let [o2 (t2' input <=)
        co2 (t2' input >)]
    (->> [o2 co2]
         (map (comp parse-bin str/join))
         (apply *))))

(defn -main
  [& _]
  (println ((juxt t1 t2) input)))

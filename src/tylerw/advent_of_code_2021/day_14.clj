(ns tylerw.advent-of-code-2021.day-14
  (:require [net.cgrand.xforms :as x]
            [tylerw.advent-of-code-2021.utils :as u]))

(defn str->pairs
  [s]
  (frequencies (map str s (rest s))))

(let [source (u/day-input-source 14)
      starting-point (x/some identity source)
      rule-xf (comp (drop 2)
                    (map (fn [l] (let [[k v] (re-seq #"\w+" l)]
                                   [k (first v)]))))]
  (def polys-and-pairs ((juxt frequencies str->pairs) starting-point))
  (def rules (into {} rule-xf source)))

(defn step
  [[polymers pairs]]
  (-> (fn [[polymers pairs] [a b :as ab] n]
        (let [c (rules ab)]
          [(update polymers c (fnil (partial + n) n))
           (merge-with + pairs {(str a c) n (str c b) n})]))
      (reduce-kv [polymers {}] pairs)))

(defn t0
  [polys-and-pairs n]
  (let [[polymers _] (-> (iterate step polys-and-pairs) (nth n))
        vv (into (sorted-set) (vals polymers))]
    (apply - ((juxt last first) vv))))

(def t1 (t0 polys-and-pairs 10))
(def t2 (t0 polys-and-pairs 40))

(defn -main
  [& _]
  (println [t1 t2]))

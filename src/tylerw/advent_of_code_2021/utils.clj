(ns tylerw.advent-of-code-2021.utils
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.core.matrix :as m]
            [net.cgrand.xforms.io :as xio]))

;;; input
(defn day-input-resource
  "A day's input file."
  [n]
  (->> n (format "input/day-%02d") io/resource))

(defn day-input-source
  "A reducible view of a day's input (suitable for a transducer source).

  Note: one must use a method with an IReduce interface to extract values. So
  sequence, for example, will not work. But {re,trans}duce, into, etc. will."
  [n]
  (-> n day-input-resource xio/lines-in))


(defn read-string-as-vec
  "Wrap a string s in brackets and read it."
  [s]
  (-> (str "[" s "]") edn/read-string))

;;; numbers
;; parse-int
; based on 1.11's parse-long (and parsing-err)
(defn parse-int
  "Parse string of decimal digits with optional leading -/+ and return an
  Integer value, or nil if parse fails"
  (^Integer [^String s] (parse-int s 10))
  (^Integer [^String s ^Integer radix]
  (if (string? s)
    (try
      (Integer/valueOf s radix)
      (catch NumberFormatException _ nil))
    (throw (IllegalArgumentException.
             (str "Expected string, got "
                  (if (nil? val)
                    "nil"
                    (-> val class .getName))))))))

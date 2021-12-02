(ns tylerw.advent-of-code-2021.utils
  (:require [clojure.java.io :as io]
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


;;; numbers
;; parse-int
; based on 1.11's parse-long (and parsing-err)
(defn parse-int
  "Parse string of decimal digits with optional leading -/+ and return an
  Integer value, or nil if parse fails"
  ^Integer [^String s]
  (if (string? s)
    (try
      (Integer/valueOf s)
      (catch NumberFormatException _ nil))
    (throw (IllegalArgumentException.
             (str "Expected string, got "
                  (if (nil? val)
                    "nil"
                    (-> val class .getName)))))))

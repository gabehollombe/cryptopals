(ns cryptopals.crack
  (:require [cryptopals.core :refer :all])
  (:require [cryptopals.detect :as detect])
  (:require [cryptopals.crypt :as crypt]))

(defn crack [crack-pred ciphertext]
  (loop [ciphertext ciphertext
         k 0]
    (let [text (crypt/decrypt ciphertext k)]
     (cond
       (> k 255) nil
       (crack-pred text) [k text]
       :else  (recur ciphertext (+ k 1))))))

(defn detect-single-char-xor [file_path]
  (with-open [rdr (clojure.java.io/reader file_path)]
    (let [lines (line-seq rdr)
            cracked-lines (filter identity (map crack lines))]
      (println cracked-lines)
      )))

(defn edit-distance-for-keysize [bytes-arr keysize]
  (let [block-a (take keysize bytes-arr)
        block-b (take keysize (nthrest bytes-arr keysize))
        edit-distance (hamming-distance (flatten block-a) (flatten block-b))
        normalized (/ edit-distance keysize)
        ]
    normalized))

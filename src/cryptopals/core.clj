(ns cryptopals.core
  (:require [cryptopals.detect :as detect])
  (:require [clojure.pprint :refer [cl-format]]))

(import org.apache.commons.codec.binary.Base64)
(import org.apache.commons.codec.binary.Hex)

(defn hex [s]
  ;; In: ASCII string
  ;; Out: Hexed string
  (Hex/encodeHexString (.getBytes (apply str s))))

(defn dehex [hex-str]
  ;; In: Hex encoded string
  ;; Out: Byte[] of values from hex
   (Hex/decodeHex  (.toCharArray (str hex-str))))

(defn hex-to-b64  [hex-str]
  ;; In: String of hex
  ;; Out: Base64 version of hex
  ( -> (dehex hex-str)
       Base64/encodeBase64
       String.))

(defn byte-to-bits [b]
  ; In: byte
  ; Out: seq of bits with most signifigant on left
  ; Ex: 2 -> (f f f f f f t f)
  (map #(bit-test b %) (range 7 -1 -1)))

(defn str-bytes [s]
  (map (comp byte int) s))

(defn str-bits [s]
  ; In: string
  ; Out: sequence of all the bits in the string
  (flatten (map byte-to-bits (str-bytes s))))

(defn byte-to-binary-string [b]
  (cl-format nil "~8,'0',B" b))

(defn str-to-bitstring [s]
  (apply str (map byte-to-binary-string (.getBytes s))))

(defn hamming-distance [as bs]
  ; In: seqs
  ; Out: hamming distance (int) for number of differing values in two seqs
  (let [differences (filter false? (map = (flatten as) (flatten bs)))]
     (count differences)))

(defn str-bitwise-hamming-distance [a b]
  ; In: strs
  ; Out: hamming distance between 8-bit binary representations of chars in strings
  (let [a-bits (str-bits a)
        b-bits (str-bits b)]
      (hamming-distance a-bits b-bits)))

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

(defn str-bytes [s]
  (map (comp byte int) s))

(defn byte-to-binary-string [b]
  (cl-format nil "~8,'0',B" b))

(defn str-to-bitstring [s]
  (apply str (map byte-to-binary-string (.getBytes s))))

(defn str-hamming-distance [a b]
  ; In: strings
  ; Out: hamming distance (int) for number of differing chars
  (let [differences (filter false? (map = a b))]
     (count differences)))

(defn bitwise-hamming-distance [a b]
  ; In: strings
  ; Out: hamming distance between 8-bit binary representations of chars in strings
  (str-hamming-distance (str-to-bitstring a) (str-to-bitstring b)))

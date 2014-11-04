(ns cryptopals.crypt
  (:require [cryptopals.core :refer :all]))

(import org.apache.commons.codec.binary.Hex)

(defn hex-xor [a b]
  ;; In: 2 strings of hex
  ;; Out: XORed strings as new string
  (-> (map bit-xor (dehex a) (dehex b))
      byte-array
      Hex/encodeHexString))

(defn decrypt [ciphertext k]
  (let [key-length (/ (count ciphertext) 2)
        filled-key (hex (repeat key-length (char k)))]
    (String. (dehex (hex-xor ciphertext filled-key)))))

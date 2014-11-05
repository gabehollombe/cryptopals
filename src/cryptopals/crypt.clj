(ns cryptopals.crypt
  (:require [cryptopals.core :refer :all]))

(import org.apache.commons.codec.binary.Hex)

(defn hex-xor [a b]
  ;; In: 2 strings of hex
  ;; Out: XORed strings as new string
  (-> (map bit-xor (dehex a) (dehex b))
      byte-array
      Hex/encodeHexString))

(defn encrypt-repeating-xor [text k]
  (let [repeated-key (repeat (/ (count text) (count k)) k)
        hexed-text (hex text)
        hexed-key  (hex repeated-key)]
    (hex-xor hexed-text hexed-key)))

(defn decrypt [ciphertext k]
  ;; In: ciphertext as string, and key as string
  ;; Out: decrypted text as string
  (let [key-length (/ (count ciphertext) 2)
        filled-key (hex (repeat key-length (char k)))]
    (String. (dehex (hex-xor ciphertext filled-key)))))

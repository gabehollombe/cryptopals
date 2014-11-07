(ns cryptopals.crypt
  (:require [cryptopals.core :refer :all]))

(import org.apache.commons.codec.binary.Hex)

(defn xor [a b]
  ;; In: 2 seq-ables of numbers
  ;; Out: XORed byte array
  (-> (map bit-xor (seq a) (seq b))
      byte-array))

(defn hex-xor [a b]
  ;; In: 2 strings of hex
  ;; Out: XORed strings as new string
  (-> (xor (dehex a) (dehex b))
      Hex/encodeHexString))

(defn encrypt-repeating-xor [text k]
  ;; In: plaintext str and key str
  ;; Out: hexed ciphertext
  (let [repeated-key (apply str (repeat (/ (count text) (count k)) k))
        text-bytes (str-bytes text)
        repeated-key-bytes (str-bytes repeated-key)]
    (Hex/encodeHexString (xor text-bytes repeated-key-bytes))))

(defn decrypt [ciphertext k]
  ;; In: ciphertext as string, and key as string
  ;; Out: decrypted text as string
  (let [key-length (/ (count ciphertext) 2)
        filled-key (hex (repeat key-length (char k)))]
    (String. (dehex (hex-xor ciphertext filled-key)))))

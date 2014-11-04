(ns cryptopals.core
  (:require [cryptopals.detect :as detect]))

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

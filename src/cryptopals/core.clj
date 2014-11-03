(ns cryptopals.core)
(import org.apache.commons.codec.binary.Base64)
(import org.apache.commons.codec.binary.Hex)

(defn- hex [s]
  ;; In: ASCII string
  ;; Out: Hexed string
  (Hex/encodeHexString (.getBytes (apply str s))))

(defn- dehex [hex-str]
  ;; In: Hex encoded string
  ;; Out: Byte[] of values from hex
   (Hex/decodeHex  (.toCharArray (str hex-str))))

(defn hex-to-b64  [hex-str]
  ;; In: String of hex 
  ;; Out: Base64 version of hex
  ( -> (dehex hex-str)
       Base64/encodeBase64
       String.))

(defn hex-xor [a b]
  ;; In: 2 strings of hex
  ;; Out: XORed strings as new string
  (-> (map bit-xor (dehex a) (dehex b))
      byte-array
      Hex/encodeHexString))

(defn sorted-frequencies [s]
  (let [freqs (frequencies s)]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get freqs key2) key2]
                                  [(get freqs key1) key1])))
        freqs)))

(defn word-chars [s]
  (re-seq #"\w" s))

(defn- cracked? [text]
  (let [num-chars (count text)
        num-word-chars (count (word-chars text))
        ratio (/ num-word-chars num-chars)
        num-spaces (or ((frequencies text) \space) 0)]
    (and (> ratio 0.75)
     (> num-spaces 1))))

(defn decrypt [ciphertext k]
  (let [key-length (/ (count ciphertext) 2)
        filled-key (hex (repeat key-length (char k)))]
    (String. (dehex (hex-xor ciphertext filled-key)))))

(defn crack [ciphertext]
  (loop [ciphertext ciphertext 
         k 0]
    (let [text (decrypt ciphertext k)]
     (cond 
       (> k 255) nil
       (cracked? text) [k text]
       :else  (recur ciphertext (+ k 2))))))

(defn detect-single-char-xor [file_path]
  (with-open [rdr (clojure.java.io/reader file_path)]
    (let [lines (line-seq rdr)
            cracked-lines (filter identity (map crack lines))]
      (println cracked-lines)
      )))

(defn foo [file_path]
  (with-open [rdr (clojure.java.io/reader file_path)]
     (take 1 (line-seq rdr))))

(let [lines ["1212", "3434"]]
  (filter identity  (map crack lines))
  )

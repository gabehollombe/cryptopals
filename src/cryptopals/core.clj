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
  (let [freqs (frequencies (clojure.string/upper-case s))]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get freqs key2) key2]
                                  [(get freqs key1) key1])))
        freqs)))

(defn word-chars [s]
  (re-seq #"\w" s))

(defn words [s]
  (->> (clojure.string/split s #" ")
       (filter #(re-matches #"[a-zA-Z.,!'\"]*" %))))

(defn has-vowel? [s]
  (re-matches #".*[aeiouy]+.*" s))

(defn score [s]
  (let [top-letters #{\space \E \T \A \O \I \N \S \H \R \D \L \U}
        expected-percentages {\space 0.127
                              \E 0.127
                              \T 0.09
                              \A 0.081
                              \O 0.075
                              \I 0.069
                              \N 0.067
                              \S 0.063}
        length (count s)
        freqs (sorted-frequencies s)
        actual-percentages (into {} (for [[k,v] freqs] [k, (/ v length)]))
        diffs (for [expected-char (keys expected-percentages)]
                (- (expected-percentages expected-char)
                   (or (actual-percentages expected-char) 0)))
        sum-diffs (apply + diffs)
        ]
    sum-diffs))

(defn freq-score-ok? [text]
  (< ( score text) 0.25))

(defn words-with-vowels? [text]
  (let [text-length (count text)
        num-words (count (words text))
        num-words-with-vowels (count (filter has-vowel? (words text)))]
    (and (< 0 num-words)
         (< (/ text-length 10) num-words)
         (> (/ num-words-with-vowels num-words) 0.50))))

(defn decrypt [ciphertext k]
  (let [key-length (/ (count ciphertext) 2)
        filled-key (hex (repeat key-length (char k)))]
    (String. (dehex (hex-xor ciphertext filled-key)))))

(defn crack [crack-pred ciphertext]
  (loop [ciphertext ciphertext
         k 0]
    (let [text (decrypt ciphertext k)]
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

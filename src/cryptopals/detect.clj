(ns cryptopals.detect)

(defn- words [s]
  (->> (clojure.string/split s #" ")
       (filter #(re-matches #"[a-zA-Z.,!'\"]*" %))))

(defn- sorted-frequencies [s]
  (let [freqs (frequencies (clojure.string/upper-case s))]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get freqs key2) key2]
                                  [(get freqs key1) key1])))
        freqs)))

(defn- normalized-frequencies [s]
   (frequencies (clojure.string/upper-case s)))


(defn- score [s]
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


(defn- has-vowel? [s]
  (re-matches #".*[aeiouy]+.*" s))

(defn words-with-vowels? [text]
  (let [text-length (count text)
        num-words (count (words text))
        num-words-with-vowels (count (filter has-vowel? (words text)))]
    (and (< 0 num-words)
         (< (/ text-length 10) num-words)
         (> (/ num-words-with-vowels num-words) 0.50))))


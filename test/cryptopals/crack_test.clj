(ns cryptopals.crack-test
  (:use expectations)
  (:require [cryptopals.detect :refer :all])
  (:require [cryptopals.crack :refer :all]))

(expect [88 "Cooking MC's like a pound of bacon"]
        (crack words-with-vowels? "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))

(expect [88 "Cooking MC's like a pound of bacon"]
        (crack freq-score-ok? "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))


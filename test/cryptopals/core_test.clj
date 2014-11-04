(ns cryptopals.core-test
  (:use expectations)
  (:require [cryptopals.core :refer :all]))

(expect "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
        (hex-to-b64 "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"))

(expect "746865206b696420646f6e277420706c6179"
        (hex-xor "1c0111001f010100061a024b53535009181c" "686974207468652062756c6c277320657965"))

(expect [88 "Cooking MC's like a pound of bacon"]
        (crack words-with-vowels? "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))

(expect [88 "Cooking MC's like a pound of bacon"]
        (crack freq-score-ok? "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"))

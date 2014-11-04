(ns cryptopals.crypt-test
  (:use expectations)
  (:require [cryptopals.crypt :refer :all]))

(expect "746865206b696420646f6e277420706c6179"
        (hex-xor "1c0111001f010100061a024b53535009181c" "686974207468652062756c6c277320657965"))

;; (expect "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272
;;         a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
;;         (encrypt-repeating-xor "ICE" "Burning 'em, if you ain't quick and nimble
;;         I go crazy when I hear a cymbal"))

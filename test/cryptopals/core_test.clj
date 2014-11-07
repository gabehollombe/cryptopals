(ns cryptopals.core-test
  (:use expectations)
  (:require [cryptopals.core :refer :all]))

(expect "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
        (hex-to-b64 "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"))

(expect 37
        (str-bitwise-hamming-distance "this is a test" "wokka wokka!!!"))


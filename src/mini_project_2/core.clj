(ns mini-project-2.core
  (:gen-class)
  (:require
   [opennlp.nlp :refer :all]
   [opennlp.tools.filters :as filters]
   [opennlp.treebank :refer :all]
   [clojure.pprint :refer :all]))

(defrecord Profile
           [profile-name nouns verbs])

(def use-string (slurp "src/mini_project_2/text.txt"))

(def tokenize (make-tokenizer "models/en-token.bin"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/en-ner-person.bin"))

(declare alpha-filter)

; Regex filter to remove commas and periods and other symbols.
(filters/pos-filter alpha-filter #"[^.,]+")

(defn parse-to-record
  [string]
  (let [tokenized-string (tokenize string)]
    (Profile. (name-find tokenized-string) (filters/nouns (pos-tag tokenized-string)) (filters/verbs (pos-tag tokenized-string)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [record (parse-to-record use-string)]
    (pprint (frequencies (concat (.nouns record)
                                 (.verbs record))))
    ; (def word-count (count (concat (.nouns record) (.verbs record))
    ))




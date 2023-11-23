(ns mini-project-2.core
  (:gen-class)
  (:require
   [clojure.pprint :refer :all]
   [opennlp.nlp :refer :all]
   [opennlp.tools.filters :as filters]
   [opennlp.treebank :refer :all]))

;; Record for storing used names, nouns, and verbs
(defrecord Profile
           [names nouns verbs])

;; Weighted record for using the percentage of words within it.
(defrecord WeightedProfile
           [names nouns verbs])

;; Setting the string/document I plan to run the word processing on
(def use-string (slurp "src/mini_project_2/text.txt"))

;; String I plan to compare word processing on.
(def compare-string "Temp")

;; Models that are required for the tokenizer, pos-tag, and name-find functions.
(def tokenize (make-tokenizer "models/en-token.bin"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/en-ner-person.bin"))

;; Declaration of unused symbol to avoid macro warnings.
(declare alpha-filter)

;; Regex filter to remove commas and periods and other symbols.
(filters/pos-filter alpha-filter #"[^.,]+")

;; This function takes in a string and outputs a record containing the names, nouns, and verbs used.
(defn parse-to-profile
  [string]
  (let [tokenized-string (tokenize string)]
    (Profile. (name-find tokenized-string) (filters/nouns (pos-tag tokenized-string)) (filters/verbs (pos-tag tokenized-string)))))

(defn parse-frequencies-to-weighted-profile
  [profile word-count]
  (map
   (fn [[_ v]]
     (/ v word-count)) profile))

(defn compare-weighted-profiles
  [profile1 profile2]
  ;; Compare word weights against eachother to catch commonly used nouns and verbs
  )
;; Currently just shows how the record looks.
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [record (parse-to-profile use-string)]
    (pprint (reverse (sort-by (fn [[_ v]] v) (frequencies (concat (.nouns record)
                                                                  (.verbs record)
                                                                  (.names record))))))

    ;;   (def profile1 (parse-frequencies-to-weighted-profile profile-with-frequencies (count (concat
    ;;                                                                          (.nouns record)
    ;;                                                                         (.verbs record)
    ;; (def profile2 (same methods))                                                                            (.names record)))))

;; (compare-weighted-profiles profile1 profile2)
    ))

(ns mini-project-2.core
  {:clj-kondo/lint-as 'clojure.core/let}
  (:gen-class)
  (:require
   [opennlp.nlp :refer :all]
   [opennlp.tools.filters :as filters]
   [opennlp.treebank :refer :all]))

(defrecord Profile
           [profile-name nouns verbs])

(def use-string "long ass string")

(def tokenize (make-tokenizer "models/en-token.bin"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/en-ner-person.bin"))

(defn parse-to-record
  [string]
  (let [tokenized-string (tokenize string)]
    (Profile. (name-find tokenized-string) (filters/nouns (pos-tag tokenized-string)) (filters/verbs (pos-tag tokenized-string)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (parse-to-record use-string))

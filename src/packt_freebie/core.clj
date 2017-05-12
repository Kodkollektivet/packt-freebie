(ns packt-freebie.core
  (:gen-class)
  (:require [net.cgrand.enlive-html :as html]
             [clj-http.client :as http]))

(defn get-dom
  "Returns a Dom-like structure for the main-page"
  [my-cs]
  (html/html-snippet
   (:body (http/get "https://www.packtpub.com/packt/offers/free-learning" {:cookie-store my-cs}))))

(defn extract-title
  "Takes the dom-like structure and extracts the actual book title"
  [dom]
  ;; Using macro here to make the code easier to read
  (-> (html/select dom [:div.dotd-title :h2])
      first
      :content
      first
      clojure.string/trim
      ))

(defn auth
  "Logs in and returns a cookie-store to be used for successive requests"
  [user pass]
  (let [my-cs (clj-http.cookies/cookie-store)
        login (http/post "https://www.packtpub.com/"
               {:form-params {:email user
                              :password pass
                              :op "login"
                              :form_build_id "form-cf524c8c10d7ada4418e02c03fa39493"
                              :form_id "packt_user_login_form"}
                :cookie-store my-cs})]
    my-cs))

(defn get-user
  "Gets the username of the logged in user"
  [my-cs]
  (-> (http/get "https://www.packtpub.com/account" {:cookie-store my-cs})
        :body
        html/html-snippet
        (html/select [:h1])
        first
        :content
        first))

(defn extract-claim-link
  "Takes a dom-structure as input and extracts the claim-link of the day"
  [dom]
  ;; First extract the relative link
  (let [rel-link
        (-> dom
            (html/select [:a.twelve-days-claim])
            first
            :attrs
            :href)]
 
    ;; Add the original address to the relative link before returning
    (str "https://www.packtpub.com" rel-link)))

(defn claim-book
  "Claims the book of the day"
  [dom my-cs]
  (http/get (extract-claim-link dom) {:cookie-store my-cs}))

(defn get-credentials
  "Extracts credentials from a config-file and returns a 2 element vector with user and password."
  [file]
  (-> file
      slurp
      clojure.string/trim
      (clojure.string/split #" ")))

(defn -main
  "Prints out the e-book title of the day"
  [& args]
  (let [cred (get-credentials (first args))
        user (first cred) pass (second cred)
        cs (auth user pass)
        main-dom (get-dom cs)]
    
    ;; for debugging purposes
    (println (str "Logged in as user: " (get-user cs)))
    (println (str "Free e-book of the day: " (extract-title main-dom)))
    (println (str "Claim Link: " (extract-claim-link main-dom)))
    (println "Claiming free e-book of the day...")
    (claim-book main-dom cs)))

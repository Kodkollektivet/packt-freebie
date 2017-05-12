(defproject packt-freebie "0.1.0-SNAPSHOT"
  :description "Automates the task of claiming the free e-book of the day at packt."
  :url ""
  :license {:name "GPLv3"
            :url "https://www.gnu.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [enlive "1.1.6"]
                 [clj-http "3.5.0"]]
  :main ^:skip-aot packt-freebie.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

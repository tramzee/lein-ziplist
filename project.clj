(defproject lein-ziplist "0.1.0-SNAPSHOT"
  :description "Some deploy and config management tools"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[ragtime "0.5.2" :exclusions [org.clojure/java.jdbc]]
                 [environ "1.0.1" :exclusions [org.clojure/clojure]]
                 [org.clojure/java.jdbc "0.4.2"]]
  :eval-in-leiningen true)

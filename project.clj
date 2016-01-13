(defproject hello "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :source-paths ["src/clj/main" "src/clj/config"]
  :resource-paths ["resources/public"]
  :dependencies [[org.clojure/clojure "1.8.0-RC4"]
                 [org.clojure/clojurescript "1.7.228"]
                 [miraj/html "5.1.0-SNAPSHOT"]
                 [polymer/core "1.2.3-SNAPSHOT"]
                 [polymer/dom "1.2.3-SNAPSHOT"]
                 [polymer/iron "1.2.3-SNAPSHOT"]
                 [polymer/paper "1.2.3-SNAPSHOT"]
                 [polymer/platinum "1.2.3-SNAPSHOT"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.2"]]
  :cljsbuild {
    :builds [{:source-paths ["src/cljs"]
              :compiler {:output-to "resources/public/scripts/main.js"
                         :output-dir "resources/public/scripts/tmp"
                         :optimizations :whitespace
                         :pretty-print true}}]}
  :ring {:handler core/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]
         :resource-paths ["resources/public"]}})

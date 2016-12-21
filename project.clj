(defproject dungeon-crawl "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0"]
                 [re-frame "0.9.0-beta1"]
                [im.chit/vinyasa "0.4.7"]
                                  [com.taoensso/timbre "4.8.0"]

                 [binaryage/devtools "0.8.3"]
                 [binaryage/dirac "0.8.6"]]

   :injections [ (require '[vinyasa.inject :as inject])
                                  (require '[taoensso.timbre])
		                              (inject/in  cljs.core  > [taoensso.timbre  *config* *context* -elide? -levels-map -levels-set -levels-vec -log! -log-and-rethrow-errors -log-errors -logged-future -spy color-str compile-ns-filter debug debugf default-err default-out default-output-fn default-timestamp-opts error errorf example-config fatal fatalf get-?hostname get-?hostname_ get-env get-hostname handle-uncaught-jvm-exceptions! info infof level>= log log! log* log-and-rethrow-errors log-env log-errors log? logf logf* logged-future logging-enabled? logp may-log? merge-config! ordered-levels println-appender refer-timbre report reportf set-config! set-level! sometimes spit-appender spy stacktrace str-println swap-config! trace tracef valid-level valid-level? warn warnf with-config with-context with-default-outs with-level with-log-level with-logging-config with-merged-config])]

  :plugins [[lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; the presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {:on-jsload "dungeon-crawl.core/on-js-reload"
                           ;; :open-urls will pop open your application
                           ;; in the default browser once Figwheel has
                           ;; started and complied your application.
                           ;; Comment this out once it no longer serves you.
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:main dungeon-crawl.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/dungeon_crawl.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           ;; Dirac runtime   devtools in Chrome
                           ;; https://github.com/binaryage/dirac
                           :preloads [devtools.preload dirac.runtime.preload]}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/dungeon_crawl.js"
                           :main dungeon-crawl.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this

             ;; doesn't work for you just run your own server :) (see lein-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             }


  ;; setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl


  :profiles {:dev {:dependencies [[binaryage/devtools "0.8.3"]
                                  [org.clojure/clojure "1.8.0"]
                                  [com.taoensso/timbre "4.8.0"]
                                  [im.chit/vinyasa "0.4.7"]
                                  [org.clojure/clojurescript "1.9.229"]
                                  [binaryage/dirac "0.8.6"]
                                  [figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER

                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init (do
                                     (require 'dirac.agent)
                                     (dirac.agent/boot!))
                                  :port 8230
                                  :nrepl-middleware [dirac.nrepl/middleware]}}}

)

(ns leiningen.ziplist
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]
            [environ.core :refer [env]]
            [leiningen.core.eval :as eval]
            ))




(defn load-config []
  {:datastore  (jdbc/sql-database (env :jdbc-connection-string ))
   :migrations (jdbc/load-resources "migrations")})

(defn migrate [args]
  (repl/migrate (load-config)))

(defn rollback [args]
  (repl/rollback (load-config)))


(defn bump-scraper
  [project [scraper-version]]
  (binding [eval/*dir* (:root project)]
    (let [curver (second (first (filter #(= (first %) 'cnscrape/cnscrape)
                                 (:dependencies project))))
          sedcmd (format "s/\\(cnscrape *\\\"\\).*\\\"/\\1%s\\\"/" scraper-version)]
      (when-not (= scraper-version curver)
        (println "updating to" scraper-version "from" curver)
        (eval/sh-with-exit-code "Could not sed" "sed" "-i.bsbak" sedcmd "project.clj")))))

(defn ziplist
  "I don't do a lot."
  {:subtasks [#'migrate #'rollback #'bump-scraper]}
  [project & [subtask & args]]
  (case subtask
    "migrate" (migrate args)
    "rollback" (rollback args)
    "bump-scraper" (bump-scraper project args)
    (println "Hi there everyone!")))

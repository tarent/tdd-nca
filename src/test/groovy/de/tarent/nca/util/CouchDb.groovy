package de.tarent.nca.util


import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait

class CouchDb {

    static CouchDb instance = new CouchDb()

    volatile boolean started = false

    boolean useExternalCouchDb = shoudlUseExternalCouchDB()

    def couchDb

    String url

    CouchDb() {
        if (useExternalCouchDb) {
            println("Using external couch DB")
        } else {
            println("Using couch DB via testcontainers")
            couchDb = new GenericContainer("couchdb")//:2.3.1")
                    .withExposedPorts(5984)
                    .waitingFor(Wait.forHttp("/").forPort(5984))
        }
    }

    def start() {
        if (!useExternalCouchDb) {
            synchronized (couchDb) {
                if (!started) {
                    println("Starting couch DB ...")
                    couchDb.start()
                    println("couch DB url: ${getUrl()}}")
                    started = true
                }
            }
        }
    }

    def stop() {
        if (!useExternalCouchDb) {
            if (started) {
                println("Stopping couch DB ...")
                couchDb.stop()
                started = false
            }
        }
    }

    String getUrl() {
        if (useExternalCouchDb) {
            System.getenv('couchDbUrl') ?: System.getProperty('couchDbUrl') ?: 'http://localhost:5984/'
        } else {
            "http://${couchDb.getContainerIpAddress()}:${couchDb.getMappedPort(5984)}/"
        }
    }

    boolean shoudlUseExternalCouchDB() {
        def value = (System.getenv('useExternalCouchDB') ?: System.getProperty('useExternalCouchDB'))?.toLowerCase()
        println("useExternalCouchDB=$value")
        value == "true"
    }
}

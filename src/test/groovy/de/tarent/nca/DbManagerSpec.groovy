package de.tarent.nca

import de.tarent.nca.testutil.CouchDb
import de.tarent.nca.testutil.NeedsCouchDb
import spock.lang.Specification

import static de.tarent.nca.testutil.RestClientFactory.createRESTClient

@NeedsCouchDb
class DbManagerSpec extends Specification {

    def restClient

    def setup() {
        restClient = createRESTClient(CouchDb.instance.getUrl())
    }

    def 'can create database'() {
        given:
        def manager = new DbManager(URI.create(CouchDb.instance.getUrl()))

        def dbName = "db-${UUID.randomUUID()}"
        when:
        DB db = manager.createDb(dbName)

        then:
        def result = restClient.get(path: dbName)
        result.status == 200
        result.data.db_name == dbName
        db.name == dbName

        cleanup:
        restClient.delete(path: dbName)
    }
}

package de.tarent.nca

import de.tarent.nca.testutil.CouchDb
import de.tarent.nca.testutil.NeedsCouchDb
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import static de.tarent.nca.testutil.RestClientFactory.createRESTClient

@NeedsCouchDb
class DBManagerSpec extends Specification {

    def restClient

    def setup() {
        restClient = createRESTClient(CouchDb.instance.getUrl())
    }

    def 'can create a database' () {
        given:
        def dBManager = new DBManager(URI.create(CouchDb.instance.getUrl()))
        def dbName = "db-${UUID.randomUUID().toString()}"

        when:
        def db = dBManager.createDatabase(dbName)

        then:
        def result = restClient.get(path: dbName)
        result.status == 200
        result.data.db_name == dbName
        db.name == dbName

        cleanup:
        if (dbName != null) {
            assert restClient.delete(path: dbName).status == 200
        }
    }

}

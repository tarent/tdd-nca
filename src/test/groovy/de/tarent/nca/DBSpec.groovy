package de.tarent.nca

import de.tarent.nca.testutil.CouchDb
import de.tarent.nca.testutil.NeedsCouchDb
import spock.lang.Specification

import static de.tarent.nca.testutil.RestClientFactory.createRESTClient

@NeedsCouchDb
class DBSpec extends Specification {

    def restClient

    def setup() {
        restClient = createRESTClient(CouchDb.instance.getUrl())
    }

    def 'can create document'() {
        given:
        def manager = new DbManager(URI.create(CouchDb.instance.getUrl()))
        def dbName = "db-${UUID.randomUUID()}"
        DB db = manager.createDb(dbName)

        when:
        def id = db.createDocument(new Document("SomeDocument"));

        then:
        def result = restClient.get(path: "${dbName}/${id}")
        result.status == 200

        result.data.name == "SomeDocument"

        cleanup:
        restClient.delete(path: dbName)
    }

}

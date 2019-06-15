package de.tarent.nca

import de.tarent.nca.testutil.CouchDb
import de.tarent.nca.testutil.NeedsCouchDb
import spock.lang.Specification

import java.time.LocalDateTime

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

        def dbName = "db-${System.currentTimeMillis()}-${UUID.randomUUID()}"
        def docId = "doc-${UUID.randomUUID()}"
        DB db = manager.createDb(dbName)

        when:
        Document doc = db.createDocument(docId);

        then:
        doc != null;
        doc.id == docId;

        def result = restClient.get(path: "${dbName}/${docId}")
        result.status == 200

        cleanup:
        restClient.delete(path: dbName)
    }

}

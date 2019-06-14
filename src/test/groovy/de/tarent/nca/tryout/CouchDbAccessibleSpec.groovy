package de.tarent.nca.tryout

import de.tarent.nca.testutil.CouchDb
import de.tarent.nca.testutil.NeedsCouchDb
import de.tarent.nca.testutil.RestClientFactory
import groovyx.net.http.RESTClient
import spock.lang.Specification

import static de.tarent.nca.testutil.RestClientFactory.createRESTClient

@NeedsCouchDb
class CouchDbAccessibleSpec extends Specification {

    def restClient

    def setup() {
        restClient = createRESTClient(CouchDb.instance.getUrl())
    }

    def cleanup() {
    }

    def 'test couch db is responsive'() {
        when:
        def response = restClient.get(path: '/')

        then:
        response.status == 200
        response.data.couchdb == 'Welcome'
    }

}

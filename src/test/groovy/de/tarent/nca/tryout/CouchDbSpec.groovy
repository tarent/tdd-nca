package de.tarent.nca.tryout

import de.tarent.nca.util.CouchDb
import de.tarent.nca.util.NeedsCouchDb
import groovyx.net.http.RESTClient
import spock.lang.Specification

@NeedsCouchDb
class CouchDbSpec extends Specification {

    def restClient

    def setup() {
        restClient = new RESTClient(CouchDb.instance.getUrl())
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

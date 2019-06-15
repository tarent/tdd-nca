package de.tarent.nca.util

import spock.lang.Specification

class URIUtilSpec extends Specification {


    def 'allow appending a relative path'() {
        given:
        URI uri = new URI('http://localhost/basePath')

        when:
        def newUri = URIUtil.appendPath(uri, 'subPath')

        then:
        uri.toString() == 'http://localhost/basePath'
        newUri.toString() == 'http://localhost/basePath/subPath'
    }
}

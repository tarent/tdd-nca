package de.tarent.nca.testutil;

import groovyx.net.http.ParserRegistry
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse

class RestClientFactory {

    static RESTClient createRESTClient(Object url) {
        def client = new RESTClient(url)
        client.parsers = new ParserRegistry() {
            @Override
            Object parseJSON(HttpResponse resp) throws IOException {
                if (resp.entity.contentLength <= 0) {
                    null
                } else {
                    super.parseJSON(resp)
                }
            }
        }
        client.handler.failure = client.handler.success
        client
    }

}
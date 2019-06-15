package de.tarent.nca;

import de.tarent.nca.util.JsonConverter;
import de.tarent.nca.util.URIUtil;
import groovy.lang.GString;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static de.tarent.nca.util.URIUtil.appendPath;
import static java.net.http.HttpRequest.BodyPublishers.noBody;

public class DB {

    private final String name;
    private final URI dbUri;
    HttpClient client = HttpClient.newHttpClient();


    public DB(String name, URI dbUri) {
        this.name = name;
        this.dbUri = dbUri;

    }

    public String getName() {
        return name;
    }

    public String createDocument(Document doc) {
        String id = "doc-" + UUID.randomUUID();
        URI docUri = getDocumentURI(id);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(docUri)
            .PUT(HttpRequest.BodyPublishers.ofString(new JsonConverter().stringOf(doc)))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException(String.format("Request to '%s' failed: status=%d, body='%s'", docUri.toString(), response.statusCode(), response.body()));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    private URI getDocumentURI(String docName) {
        return appendPath(dbUri, docName);
    }
}

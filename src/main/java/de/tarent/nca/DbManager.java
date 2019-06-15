package de.tarent.nca;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.noBody;

public class DbManager {

    HttpClient client = HttpClient.newHttpClient();

    URI baseUri;

    public DbManager(URI baseUri) {
        this.baseUri = baseUri;
    }

    public DB createDb(String name) {
        URI dbUri = baseUri.resolve(name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(dbUri)
                .PUT(noBody())
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            return new DB(name, dbUri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

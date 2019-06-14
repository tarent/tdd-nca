package de.tarent.nca;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.noBody;

public class DBManager {

    private URI baseUri;

    private HttpClient client = HttpClient.newHttpClient();

    public DBManager(URI baseUri) {
        this.baseUri = baseUri;
    }

    public URI getBaseUri() {
        return baseUri;
    }

    public DB createDatabase(String name) {
        URI dbUri = baseUri.resolve(name);
        System.out.println(dbUri);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(dbUri)
                .PUT(noBody())
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            return new DB(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

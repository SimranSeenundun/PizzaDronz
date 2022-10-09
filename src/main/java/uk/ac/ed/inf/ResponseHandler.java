package uk.ac.ed.inf;

import org.json.JSONArray;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class ResponseHandler {

    public static String getResponse(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .join();
    }

    public static JSONArray getJSonResponse(String url) {
        String response = getResponse(url);
        return new JSONArray(response);
    }
}

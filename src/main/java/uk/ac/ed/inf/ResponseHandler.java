package uk.ac.ed.inf;

import org.json.JSONArray;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class ResponseHandler {

    /**
     * Gets the response from the provided server.
     * @param url string that points to the web server.
     * @return string response from the server.
     */
    public static String getResponse(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .join();
    }

    /**
     * Gets the JSON response from a provided server.
     * @param url string that points the web server.
     * @return JSONArray response form the server.
     */
    public static JSONArray getJSonResponse(String url) {
        String response = getResponse(url);
        return new JSONArray(response);
    }
}

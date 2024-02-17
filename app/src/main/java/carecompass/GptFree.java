package carecompass;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.http.HttpClient;

public class GptFree {
    public static void main(String[] args) {
        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgptfree.ai/"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the HTTP request and capture the response
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Handle the response
        if (response != null) {
            int statusCode = response.statusCode();
            String responseBody = response.body();
            System.out.println("Status code: " + statusCode);
            System.out.println("Response body: " + responseBody);
        } else {
            System.out.println("No response received");
        }
    }
}

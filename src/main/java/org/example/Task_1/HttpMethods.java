package org.example.Task_1;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.*;

public class HttpMethods {
    public void get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://jsonplaceholder.typicode.com/users"))
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("user.json")))
                .build();
        HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void put() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("user.json")))
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void delete() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .DELETE()
                .build();
        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
    }

    public void getUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .GET()
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void getUserByName() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users?username=Kamren"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void getComments(int userId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + userId + "/posts"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();

        Type mapType = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = new Gson().fromJson(array.get(array.size() - 1), mapType);
        int lastPostId = Integer.parseInt(map.get("id"));

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments"))
                .build();

        HttpResponse<Path> response2 =
    client.send(request2, HttpResponse.BodyHandlers.ofFile(Path.of("user-" + userId + "-post-" + lastPostId + "-comments.json")));
        System.out.println(response2.statusCode());
    }

    public void getToDos(int userId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + userId + "/todos"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();

        String result = "";
        StringBuilder builder = new StringBuilder(result);
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();
        for (int i = 0; i < array.size(); i++) {
            Map<String, String> map = new Gson().fromJson(array.get(i), mapType);
            if (map.get("completed").equals("false")){
                builder.append(array.get(i));
            }
        }
        System.out.println(builder);
    }
}

package com.rodaxsoft.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodaxsoft.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class communicationHTTP {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private String TokenActuel;
    private String RefreshTokenActuel;


    public void Signup(ApplicationUser applicationUser) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(applicationUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONWebToken tokenObj = mapper.readValue(response.body(), JSONWebToken.class);
            this.TokenActuel = tokenObj.getJwt();
            this.RefreshTokenActuel = tokenObj.getRefresh_Token();

        } else {
            throw new RuntimeException("Échec de connexion : " + response.statusCode());
        }
    }



    public void login(Credentials credentials) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(credentials);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/access-tokens"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONWebToken tokenObj = mapper.readValue(response.body(), JSONWebToken.class);
            this.TokenActuel = tokenObj.getJwt();
            this.RefreshTokenActuel = tokenObj.getRefresh_Token();

        } else {
            throw new RuntimeException("Échec de connexion : " + response.statusCode());
        }
    }

    public Profile getMe() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/me"))
                .header("x-access-token",TokenActuel)
                .GET().build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            return mapper.readValue(response.body(), Profile.class);
        }else{
            throw new RuntimeException("Echec de connexion : "+response.statusCode());
        }

    }

    public void refreshTokens() throws IOException, InterruptedException {

        var bodyMap = java.util.Map.of("refresh_token", RefreshTokenActuel);
        String jsonBody = mapper.writeValueAsString(bodyMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/access-tokens/refresh"))
                .header("Content-Type", "application/json")
                .header("x-access-token",TokenActuel)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONWebToken tokenObj = mapper.readValue(response.body(), JSONWebToken.class);
            this.TokenActuel = tokenObj.getJwt();
            System.out.println(TokenActuel);
        } else {
            throw new RuntimeException("Erreur de refresh : " + response.statusCode());
        }
    }

    public void logout() throws IOException, InterruptedException {
        var bodyMap = java.util.Map.of("refresh_token", RefreshTokenActuel);
        String jsonBody = mapper.writeValueAsString(bodyMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/access-tokens"))
                .header("Content-Type", "application/json")
                .header("x-access-token",TokenActuel)
                .method("DELETE",HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Logout succeed");
            this.TokenActuel = null;
            this.RefreshTokenActuel = null;
        } else {
            throw new RuntimeException("Code erreur : " + response.statusCode() + " - " + response.body());
        }
    }

    public List<Task> GetAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("x-access-token",TokenActuel)
                .GET().build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<Task> tasks = mapper.readValue(response.body(), new TypeReference<List<Task>>() {});

            System.out.println(tasks.size() + " tasks get.");

            return tasks;
        } else {
            throw new RuntimeException("GetAll error : API cant be reach");
        }
    }

    public void create(Task task) throws IOException, InterruptedException {
        String jsonBody = mapper.writeValueAsString(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .header("x-access-token",TokenActuel)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            System.out.println("Task create successfully");
        }else{
            throw new RuntimeException("Error in create : "+response.statusCode());
        }
    }

    public void update(String id, Task task) throws IOException, InterruptedException {

        String jsonBody = mapper.writeValueAsString(task);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)) // Utilisation de PUT
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Tâche mise à jour avec succès !");
        } else {
            throw new RuntimeException("Error in update : " + response.statusCode());
        }
    }

    public void delete(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/"+id))
                .DELETE().build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()==200){
            System.out.println("Suppression réussi");
        }
        else{
            throw new RuntimeException("Error in delete : "+response.statusCode());
        }
    }
}
package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        String sessionId = httpHeaders.getFirst("Set-Cookie").split(";")[0];
        HttpHeaders headersWithSession = new HttpHeaders();
        headersWithSession.set("Cookie", sessionId);

        User user = new User(3L,"James","Brown", (byte) 33);
        HttpEntity<User> addUserRequest = new HttpEntity<>(user, headersWithSession);
        ResponseEntity<String> addUserResponse = restTemplate.postForEntity(url, addUserRequest, String.class);

        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> updateUserRequest = new HttpEntity<>(user, headersWithSession);
        ResponseEntity<String> updateUserResponse = restTemplate.exchange(url, HttpMethod.PUT, updateUserRequest, String.class);

        String newUrl = "http://94.198.50.185:7081/api/users/3";
        HttpEntity<User> deleteUserRequest = new HttpEntity<>(user, headersWithSession);
        ResponseEntity<String> deleteUserResponse = restTemplate.exchange(newUrl, HttpMethod.DELETE, deleteUserRequest, String.class);

        System.out.println(addUserResponse.getBody() + updateUserResponse.getBody() + deleteUserResponse.getBody());

    }
}
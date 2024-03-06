package com.tvt.recompileapi.service.impl;

import com.tvt.recompileapi.dto.AccessTokenResponse;
import com.tvt.recompileapi.service.SpotifyAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpotifyAuthServiceImpl implements SpotifyAuthService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;
    @Override
    public Mono<AccessTokenResponse> getToken() {
        WebClient webClient = WebClient.create("https://accounts.spotify.com");

        String clientCredentials = clientId + ":" + clientSecret;
        String base64Credentials = java.util.Base64.getEncoder().encodeToString(clientCredentials.getBytes());


        return webClient.post()
                .uri("/api/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(AccessTokenResponse.class);
    }
}

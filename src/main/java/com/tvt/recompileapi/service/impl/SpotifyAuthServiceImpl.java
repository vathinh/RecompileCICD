package com.tvt.recompileapi.service.impl;

import com.tvt.recompileapi.dto.*;
import com.tvt.recompileapi.service.SpotifyAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Random;

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

    @Override
    public Mono<SpotifyShowResponse> getPlaylistItems() {
        return getToken().flatMap(accessTokenResponse -> {
            String bearerToken = "Bearer " + accessTokenResponse.getAccess_token();

            WebClient playlistWebClient = WebClient.create("https://api.spotify.com");
            return playlistWebClient.get()
                    .uri("/v1/playlists/{playlist_id}/tracks", "2FJrcIF7oknARHUAgYlWTE")
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
                    .retrieve()
                    .bodyToMono(SpotifyShowResponse.class);
        });
    }

    @Override
    public Mono<Song> getRandomTrack() {
        return getPlaylistItems()
                .map(SpotifyShowResponse::getItems)
                .flatMapMany(Flux::fromIterable)
                .map(Items::getTrack)
                .map(track -> {
                    Song song = new Song();
                    song.setSongName(track.getName());
                    song.setSongUrl(track.getExternal_urls().getSpotify());
                    return song;
                }).collectList()
                .flatMap(songs -> {
                    int random = new Random().nextInt(songs.size());
                    return Mono.just(songs.get(random));
                });

    }

}

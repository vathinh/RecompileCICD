package com.tvt.recompileapi.controller;


import com.tvt.recompileapi.dto.AccessTokenResponse;
import com.tvt.recompileapi.dto.SpotifyShowResponse;
import com.tvt.recompileapi.service.SpotifyAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TestingController {

    private final SpotifyAuthService spotifyAuthService;

    @GetMapping("/getToken")
    public Mono<AccessTokenResponse> getToken() {
        return spotifyAuthService.getToken();
    }

    @GetMapping("/getPlaylist")
    public Mono<SpotifyShowResponse> getPlaylist() {
        return spotifyAuthService.getPlaylistItems();
    }
}

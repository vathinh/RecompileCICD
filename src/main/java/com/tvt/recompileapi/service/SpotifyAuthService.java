package com.tvt.recompileapi.service;

import com.tvt.recompileapi.dto.AccessTokenResponse;
import com.tvt.recompileapi.dto.Song;
import com.tvt.recompileapi.dto.SpotifyShowResponse;
import reactor.core.publisher.Mono;

public interface SpotifyAuthService {
    Mono<AccessTokenResponse> getToken();

    Mono<SpotifyShowResponse> getPlaylistItems();

    Mono<Song> getRandomTrack();

}

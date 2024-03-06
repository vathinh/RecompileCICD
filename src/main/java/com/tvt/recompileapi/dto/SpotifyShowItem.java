package com.tvt.recompileapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyShowItem {
    private boolean collaborative;
    private String description;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<ImageSpotify> images;
    private String name;
    private Owner owner;
    private boolean isPublic;
    private String snapshot_id;
    private Tracks tracks;
    private String type;
    private String uri;

}

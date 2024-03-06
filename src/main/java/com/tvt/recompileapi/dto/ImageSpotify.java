package com.tvt.recompileapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageSpotify {
    private String url;
    private int height;
    private int width;
}

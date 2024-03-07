package com.tvt.recompileapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    private ExternalUrls external_urls;
    private String name;
}

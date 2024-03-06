package com.tvt.recompileapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private ExternalUrls external_urls;
    private Followers followers;
    private String href;
    private String id;
    private String type;
    private String uri;
    private String display_name;

}

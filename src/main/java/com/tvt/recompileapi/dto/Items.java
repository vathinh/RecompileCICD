package com.tvt.recompileapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    public String added_at;
    public boolean is_local;
    public Track track;
}

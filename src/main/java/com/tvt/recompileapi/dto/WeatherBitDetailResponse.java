package com.tvt.recompileapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherBitDetailResponse {
    public double app_temp;
    public int aqi;
    public String city_name;
    public int clouds;
    public String country_code;
    public String datetime;
    public double dewpt;
    public int dhi;
    public int dni;
    public double elev_angle;
    public int ghi;
    public Object gust;
    public int h_angle;
    public double lat;
    public double lon;
    public String ob_time;
    public String pod;
    public int precip;
    public double pres;
    public int rh;
    public int slp;
    public int snow;
    public int solar_rad;
    public ArrayList<String> sources;
    public String state_code;
    public String station;
    public String sunrise;
    public String sunset;
    public Double temp;
    public String timezone;
    public int ts;
    public int uv;
    public int vis;
    public Weather weather;
    public String wind_cdir;
    public String wind_cdir_full;
    public int wind_dir;
    public int wind_spd;

}

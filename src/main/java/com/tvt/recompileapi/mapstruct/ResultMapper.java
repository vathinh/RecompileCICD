package com.tvt.recompileapi.mapstruct;

import com.tvt.recompileapi.dto.Result;
import com.tvt.recompileapi.dto.WeatherBitDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultMapper {

    @Mapping(target = "city_name", source = "city_name")
    @Mapping(target = "country_code", source = "country_code")
    @Mapping(target = "datetime", source = "datetime")
    @Mapping(target = "weather", source = "weather")
    @Mapping(target = "temp", source = "temp")
    Result mapResponseToResult(WeatherBitDetailResponse weatherBitDetailResponse);

}

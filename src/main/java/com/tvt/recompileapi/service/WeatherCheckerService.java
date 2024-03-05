package com.tvt.recompileapi.service;


import com.tvt.recompileapi.dto.Result;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WeatherCheckerService {
    Mono<Result> getWeather();

    Mono<List<Result>> getWeatherWithMapstruct();
}

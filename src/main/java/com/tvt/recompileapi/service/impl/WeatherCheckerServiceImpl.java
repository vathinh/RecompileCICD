package com.tvt.recompileapi.service.impl;

import com.tvt.recompileapi.dto.Result;
import com.tvt.recompileapi.dto.WeatherBitDetailResponse;
import com.tvt.recompileapi.dto.WeatherBitResponse;
import com.tvt.recompileapi.exception.BadRequestException;
import com.tvt.recompileapi.exception.ErrorMessage;
import com.tvt.recompileapi.exception.ResourceNotFoundException;
import com.tvt.recompileapi.mapstruct.ResultMapper;
import com.tvt.recompileapi.service.WeatherCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherCheckerServiceImpl implements WeatherCheckerService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.city}")
    private String city;

    @Value("${weather.country}")
    private String country;

    private final WebClient webClient;


    private final ResultMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherCheckerServiceImpl.class);

    @Override
    public Mono<Result> getWeather() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2.0/current")
                        .queryParam("city", city)
                        .queryParam("country", country)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ErrorMessage.class)
                        .flatMap(errorMessage -> Mono.error(new BadRequestException(errorMessage.getMessage()))))
                .bodyToMono(WeatherBitResponse.class)
                .mapNotNull(weatherBitResponse -> {
                    if (weatherBitResponse.getData() != null && !weatherBitResponse.getData().isEmpty()) {
                        WeatherBitDetailResponse weatherBitDetailResponse = weatherBitResponse.getData().get(0);

                        Result res = new Result();
                        res.setCity_name(weatherBitDetailResponse.getCity_name());
                        res.setCountry_code(weatherBitDetailResponse.getCountry_code());
                        res.setDatetime(weatherBitDetailResponse.getDatetime());
                        res.setWeather(weatherBitDetailResponse.getWeather());
                        res.setTemp(weatherBitDetailResponse.getTemp());
                        return res;
                    } else {
                        throw new ResourceNotFoundException("Error the API return nothing");
                    }
                })
                .doOnError(throwable -> {
                    LOGGER.error("Error occurred during API call: {}", throwable.getMessage());
                    throw new BadRequestException("Error occurred during API call: {}" + throwable.getMessage());
                })
                .doOnSuccess(result -> {
                    if (result != null) {
                        LOGGER.info("API call succeeded. Received response: {}", result);
                    } else {
                        LOGGER.warn("API call succeeded, but no data received.");
                    }
                });
    }

    @Override
    public Mono<List<Result>> getWeatherWithMapstruct() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2.0/current")
                        .queryParam("city", city)
                        .queryParam("country", country)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(ErrorMessage.class)
                        .flatMap(errorMessage -> Mono.error(new BadRequestException(errorMessage.getMessage()))))
                .bodyToMono(WeatherBitResponse.class)
                .flatMap(response -> {
                    if (response != null && response.getData() != null) {
                        return Flux.fromIterable(response.getData())
                                .flatMap(weatherBitDetailResponse -> Mono.just(mapper.mapResponseToResult(weatherBitDetailResponse)))
                                .collectList()
                                .map(results -> results);
                    } else {
                        return Mono.error(new BadRequestException("Received null response or empty data from API"));
                    }
                })
                .doOnError(throwable -> {
                    LOGGER.error("Error occurred during API call: {}", throwable.getMessage());
                    throw new BadRequestException("Error occurred during API call: " + throwable.getMessage());
                })
                .doOnSuccess(results -> {
                    if (results != null) {
                        LOGGER.info("API call succeeded. Received responses: {}", results);
                    } else {
                        LOGGER.warn("API call succeeded, but no data received.");
                    }
                });
    }



}

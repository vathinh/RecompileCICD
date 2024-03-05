package com.tvt.recompileapi.service;

import com.tvt.recompileapi.dto.Result;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MailService {
    Mono<Boolean> sendMail(String to, String subject, List<Result> weatherResults);
}

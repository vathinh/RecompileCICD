package com.tvt.recompileapi.service;

import reactor.core.publisher.Mono;

public interface MailService {
    Mono<Boolean> sendMail(String to, String subject, String body);
}

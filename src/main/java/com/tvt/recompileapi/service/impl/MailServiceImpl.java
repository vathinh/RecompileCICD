package com.tvt.recompileapi.service.impl;

import com.tvt.recompileapi.dto.Result;
import com.tvt.recompileapi.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import reactor.core.publisher.Mono;
import org.thymeleaf.context.Context;


import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // Inject Thymeleaf's template engine

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public Mono<Boolean> sendMail(String to, String subject, List<Result> weatherResults) {
        MimeMessagePreparator mailMessage = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setFrom(senderEmail, "Auto Email By Github");
            message.addTo(to);
            message.setSubject(subject);

            // Process the HTML template
            Context thymeleafContext = new Context();
            thymeleafContext.setVariable("subject", subject);
            thymeleafContext.setVariable("weatherResults", weatherResults);
            String htmlContent = templateEngine.process("email-template.html", thymeleafContext);

            // Set the HTML content
            message.setText(htmlContent, true);
        };

        mailSender.send(mailMessage);
        return Mono.just(true);
    }
}

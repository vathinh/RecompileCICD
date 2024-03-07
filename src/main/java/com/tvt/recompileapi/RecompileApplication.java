package com.tvt.recompileapi;

import com.tvt.recompileapi.dto.Result;
import com.tvt.recompileapi.service.MailService;
import com.tvt.recompileapi.service.WeatherCheckerService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
@EnableEncryptableProperties
public class RecompileApplication implements CommandLineRunner {

    private final MailService mailService;
    private final WeatherCheckerService weatherCheckerService;
    private final String toEmail;

    @Autowired
    public RecompileApplication(MailService mailService, WeatherCheckerService weatherCheckerService, @Value("${to.email}") String toEmail) {
        this.mailService = mailService;
        this.weatherCheckerService = weatherCheckerService;
        this.toEmail = toEmail;
    }

    public static void main(String[] args) {
        SpringApplication.run(RecompileApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Mono<List<Result>> resultFromWeather = weatherCheckerService.getWeatherWithMapstruct();

        if (mailService != null) {
            resultFromWeather.flatMap(results -> mailService.sendMail(toEmail, "Weather Reminders", results)
                .doOnSuccess(mailSent -> {
                    if (mailSent) {
                        System.out.println("Mail sent successfully.");
                    } else {
                        System.out.println("Failed to send mail.");
                    }
                })).then().block(); // Ensure all reactive operations complete before exiting
//            System.exit(0);
        } else {
            System.out.println("Mail service is not available!");
        }
    }
}

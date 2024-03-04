package com.tvt.recompileapi;

import com.tvt.recompileapi.dto.Todo;
import org.springframework.web.reactive.function.client.WebClient;

public class RecompileApplication {
    public static void main(String[] args) {
        // Create WebClient instance
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

        // Fetch todos using WebClient
        Todo[] todos = webClient.get()
                .uri("/todos")
                .retrieve()
                .bodyToMono(Todo[].class)
                .block();

        // Print todos
        if (todos != null) {
            for (Todo todo : todos) {
                System.out.println(todo);
            }
        } else {
            System.out.println("No todos fetched!");
        }
        System.exit(0);

    }
}

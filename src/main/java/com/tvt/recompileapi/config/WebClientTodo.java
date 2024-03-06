package com.tvt.recompileapi.config;

import com.tvt.recompileapi.dto.Todo;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        url = "https://jsonplaceholder.typicode.com",
        name = "Todos"
)
public interface WebClientTodo {
    @GetMapping("/todos")
    List<Todo> getTodos();

    @Headers("Content-Type: application/json")
    @PostMapping("/todos")
    Todo createTodo(Todo todo);

}

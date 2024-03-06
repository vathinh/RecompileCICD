package com.tvt.recompileapi.service.impl;

import com.tvt.recompileapi.config.WebClientTodo;
import com.tvt.recompileapi.dto.Todo;
import com.tvt.recompileapi.service.GetTodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTodoServiceImpl implements GetTodoService {

    private WebClientTodo userClient;

    public GetTodoServiceImpl(WebClientTodo userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<Todo> getAllTodos() {
        return userClient.getTodos();
    }

    @Override
    public Todo create(Todo todo) {
        return userClient.createTodo(todo);
    }
}

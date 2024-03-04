package com.tvt.recompileapi.service;

import com.tvt.recompileapi.dto.Todo;

import java.util.List;

public interface GetTodoService {
    List<Todo> getAllTodos();
    Todo create(Todo todo);
}

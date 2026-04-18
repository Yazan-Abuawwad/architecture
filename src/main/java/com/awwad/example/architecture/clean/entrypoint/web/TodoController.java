package com.awwad.example.architecture.clean.entrypoint.web;

import com.awwad.example.architecture.clean.core.entity.Todo;
import com.awwad.example.architecture.clean.core.usecase.CreateTodoInputBoundary;
import com.awwad.example.architecture.clean.core.usecase.ListTodosInputBoundary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clean/todos")
public class TodoController {

    private final CreateTodoInputBoundary createTodo;
    private final ListTodosInputBoundary listTodos;

    public TodoController(CreateTodoInputBoundary createTodo, ListTodosInputBoundary listTodos) {
        this.createTodo = createTodo;
        this.listTodos = listTodos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody CreateTodoRequest request) {
        return createTodo.execute(request.title());
    }

    @GetMapping
    public List<Todo> list() {
        return listTodos.execute();
    }
}


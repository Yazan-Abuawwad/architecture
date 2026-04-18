package com.awwad.example.architecture.hexagonal.adapter.in.web;

import com.awwad.example.architecture.hexagonal.application.port.in.CreateTodoUseCase;
import com.awwad.example.architecture.hexagonal.application.port.in.ListTodosUseCase;
import com.awwad.example.architecture.hexagonal.domain.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hexagonal/todos")
public class TodoController {

    private final CreateTodoUseCase createTodoUseCase;
    private final ListTodosUseCase listTodosUseCase;

    public TodoController(CreateTodoUseCase createTodoUseCase, ListTodosUseCase listTodosUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.listTodosUseCase = listTodosUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody CreateTodoRequest request) {
        return createTodoUseCase.create(request.title());
    }

    @GetMapping
    public List<Todo> list() {
        return listTodosUseCase.list();
    }
}


package com.awwad.example.architecture.onion.presentation;

import com.awwad.example.architecture.onion.application.TodoApplicationService;
import com.awwad.example.architecture.onion.domain.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/onion/todos")
public class TodoController {

    private final TodoApplicationService todoApplicationService;

    public TodoController(TodoApplicationService todoApplicationService) {
        this.todoApplicationService = todoApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody CreateTodoRequest request) {
        return todoApplicationService.create(request.title());
    }

    @GetMapping
    public List<Todo> list() {
        return todoApplicationService.list();
    }
}


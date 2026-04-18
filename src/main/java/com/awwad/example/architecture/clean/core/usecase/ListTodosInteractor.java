package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Todo;
import com.awwad.example.architecture.clean.core.gateway.TodoGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTodosInteractor implements ListTodosInputBoundary {

    private final TodoGateway todoGateway;

    public ListTodosInteractor(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public List<Todo> execute() {
        return todoGateway.findAll();
    }
}


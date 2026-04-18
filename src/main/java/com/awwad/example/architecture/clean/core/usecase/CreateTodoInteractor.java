package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Todo;
import com.awwad.example.architecture.clean.core.gateway.TodoGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateTodoInteractor implements CreateTodoInputBoundary {

    private final TodoGateway todoGateway;

    public CreateTodoInteractor(TodoGateway todoGateway) {
        this.todoGateway = todoGateway;
    }

    @Override
    public Todo execute(String title) {
        String normalizedTitle = title == null ? "" : title.trim();
        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        return todoGateway.save(normalizedTitle);
    }
}


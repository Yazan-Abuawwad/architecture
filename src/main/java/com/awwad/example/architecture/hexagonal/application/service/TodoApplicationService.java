package com.awwad.example.architecture.hexagonal.application.service;

import com.awwad.example.architecture.hexagonal.application.port.in.CreateTodoUseCase;
import com.awwad.example.architecture.hexagonal.application.port.in.ListTodosUseCase;
import com.awwad.example.architecture.hexagonal.application.port.out.LoadTodosPort;
import com.awwad.example.architecture.hexagonal.application.port.out.SaveTodoPort;
import com.awwad.example.architecture.hexagonal.domain.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoApplicationService implements CreateTodoUseCase, ListTodosUseCase {

    private final SaveTodoPort saveTodoPort;
    private final LoadTodosPort loadTodosPort;

    public TodoApplicationService(SaveTodoPort saveTodoPort, LoadTodosPort loadTodosPort) {
        this.saveTodoPort = saveTodoPort;
        this.loadTodosPort = loadTodosPort;
    }

    @Override
    public Todo create(String title) {
        String normalizedTitle = title == null ? "" : title.trim();
        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        return saveTodoPort.save(normalizedTitle);
    }

    @Override
    public List<Todo> list() {
        return loadTodosPort.loadAll();
    }
}


package com.awwad.example.architecture.layered.service;

import com.awwad.example.architecture.layered.domain.Todo;
import com.awwad.example.architecture.layered.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(String title) {
        String normalizedTitle = title == null ? "" : title.trim();
        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        return todoRepository.save(normalizedTitle);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
}


package com.awwad.example.architecture.onion.application;

import com.awwad.example.architecture.onion.domain.model.Todo;
import com.awwad.example.architecture.onion.domain.repository.TodoRepository;
import com.awwad.example.architecture.onion.domain.service.TodoDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoApplicationService {

    private final TodoRepository todoRepository;
    private final TodoDomainService todoDomainService;

    public TodoApplicationService(TodoRepository todoRepository, TodoDomainService todoDomainService) {
        this.todoRepository = todoRepository;
        this.todoDomainService = todoDomainService;
    }

    public Todo create(String title) {
        String normalizedTitle = todoDomainService.normalizeTitle(title);
        return todoRepository.save(normalizedTitle);
    }

    public List<Todo> list() {
        return todoRepository.findAll();
    }
}


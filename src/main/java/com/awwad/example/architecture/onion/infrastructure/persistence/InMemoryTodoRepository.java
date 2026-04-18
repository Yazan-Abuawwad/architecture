package com.awwad.example.architecture.onion.infrastructure.persistence;

import com.awwad.example.architecture.onion.domain.model.Todo;
import com.awwad.example.architecture.onion.domain.repository.TodoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTodoRepository implements TodoRepository {

    private final AtomicLong ids = new AtomicLong(1);
    private final List<Todo> todos = new ArrayList<>();

    @Override
    public synchronized Todo save(String title) {
        Todo todo = new Todo(ids.getAndIncrement(), title, false);
        todos.add(todo);
        return todo;
    }

    @Override
    public synchronized List<Todo> findAll() {
        return List.copyOf(todos);
    }
}


package com.awwad.example.architecture.hexagonal.adapter.out.persistence;

import com.awwad.example.architecture.hexagonal.application.port.out.LoadTodosPort;
import com.awwad.example.architecture.hexagonal.application.port.out.SaveTodoPort;
import com.awwad.example.architecture.hexagonal.domain.Todo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryTodoPersistenceAdapter implements SaveTodoPort, LoadTodosPort {

    private final AtomicLong ids = new AtomicLong(1);
    private final List<Todo> todos = new ArrayList<>();

    @Override
    public synchronized Todo save(String title) {
        Todo todo = new Todo(ids.getAndIncrement(), title, false);
        todos.add(todo);
        return todo;
    }

    @Override
    public synchronized List<Todo> loadAll() {
        return List.copyOf(todos);
    }
}


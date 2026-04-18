package com.awwad.example.architecture.clean.dataprovider;

import com.awwad.example.architecture.clean.core.entity.Todo;
import com.awwad.example.architecture.clean.core.gateway.TodoGateway;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryTodoGateway implements TodoGateway {

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


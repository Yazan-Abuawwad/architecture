package com.awwad.example.architecture.hexagonal.application.port.out;

import com.awwad.example.architecture.hexagonal.domain.Todo;

import java.util.List;

public interface LoadTodosPort {

    List<Todo> loadAll();
}


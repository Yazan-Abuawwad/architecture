package com.awwad.example.architecture.hexagonal.application.port.in;

import com.awwad.example.architecture.hexagonal.domain.Todo;

import java.util.List;

public interface ListTodosUseCase {

    List<Todo> list();
}


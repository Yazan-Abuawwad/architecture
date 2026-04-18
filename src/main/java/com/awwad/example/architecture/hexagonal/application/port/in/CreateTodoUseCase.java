package com.awwad.example.architecture.hexagonal.application.port.in;

import com.awwad.example.architecture.hexagonal.domain.Todo;

public interface CreateTodoUseCase {

    Todo create(String title);
}


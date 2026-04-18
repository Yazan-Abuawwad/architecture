package com.awwad.example.architecture.hexagonal.application.port.out;

import com.awwad.example.architecture.hexagonal.domain.Todo;

public interface SaveTodoPort {

    Todo save(String title);
}


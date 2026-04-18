package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Todo;

public interface CreateTodoInputBoundary {

    Todo execute(String title);
}


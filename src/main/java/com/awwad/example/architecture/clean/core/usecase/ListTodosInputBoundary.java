package com.awwad.example.architecture.clean.core.usecase;

import com.awwad.example.architecture.clean.core.entity.Todo;

import java.util.List;

public interface ListTodosInputBoundary {

    List<Todo> execute();
}


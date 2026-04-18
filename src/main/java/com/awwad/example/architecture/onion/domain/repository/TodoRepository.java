package com.awwad.example.architecture.onion.domain.repository;

import com.awwad.example.architecture.onion.domain.model.Todo;

import java.util.List;

public interface TodoRepository {

    Todo save(String title);

    List<Todo> findAll();
}


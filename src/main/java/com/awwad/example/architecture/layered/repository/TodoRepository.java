package com.awwad.example.architecture.layered.repository;

import com.awwad.example.architecture.layered.domain.Todo;

import java.util.List;

public interface TodoRepository {

    Todo save(String title);

    List<Todo> findAll();
}


package com.awwad.example.architecture.clean.core.gateway;

import com.awwad.example.architecture.clean.core.entity.Todo;

import java.util.List;

public interface TodoGateway {

    Todo save(String title);

    List<Todo> findAll();
}


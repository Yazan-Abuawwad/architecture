package com.awwad.example.architecture.onion.domain.service;

import org.springframework.stereotype.Component;

@Component
public class TodoDomainService {

    public String normalizeTitle(String title) {
        String normalizedTitle = title == null ? "" : title.trim();
        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        return normalizedTitle;
    }
}


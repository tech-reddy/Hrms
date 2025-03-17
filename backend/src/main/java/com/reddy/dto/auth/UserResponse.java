package com.reddy.dto.auth;

import com.reddy.model.User;

public record UserResponse(Long id, String username, String role) {
    public UserResponse(User user) {
        this(user.getId(), user.getUsername(), user.getRole().name());
    }
}

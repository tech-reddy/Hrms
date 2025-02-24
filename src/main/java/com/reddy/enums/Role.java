package com.reddy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, USER;
    @JsonCreator
    public static Role forValue(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}

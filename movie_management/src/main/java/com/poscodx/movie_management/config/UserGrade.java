package com.poscodx.movie_management.config;

public enum UserGrade {
    NORMAL(1),
    EXPERT(2),
    ADMIN(3);

    private final int value;

    UserGrade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

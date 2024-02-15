package com.poscodx.movie_management.config;

public enum MovieGrade {
    ALL(1),
    TWELVE(2),
    FIFTEEN(3),
    NINETEEN(4);

    private final int value;

    MovieGrade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

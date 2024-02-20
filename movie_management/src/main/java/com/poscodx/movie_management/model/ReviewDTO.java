package com.poscodx.movie_management.model;

import lombok.Builder;
import lombok.Data;

@Data
public class ReviewDTO {
    private Integer reviewId;
    private UserDTO writer;
    private MovieDTO movie;
    private Integer score;
    private String content;
    private int rowCount;

    public ReviewDTO() {}
}

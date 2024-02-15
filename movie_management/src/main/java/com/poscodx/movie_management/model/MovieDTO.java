package com.poscodx.movie_management.model;

import com.poscodx.movie_management.config.MovieGrade;
import lombok.Builder;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

@Data
public class MovieDTO {
    private Integer movieId;
    private String title;
    private String description;
    private MovieGrade grade;
    private BufferedImage thumbnail;
    private List<ReviewDTO> reviewList = new LinkedList<>();

    public MovieDTO() {}

    public void setGrade(int i) {
        if(i == 1)
            this.grade = MovieGrade.ALL;
        else if(i == 2)
            this.grade = MovieGrade.TWELVE;
        else if(i == 3)
            this.grade = MovieGrade.FIFTEEN;
        else if(i == 4)
            this.grade = MovieGrade.NINETEEN;
    }
}

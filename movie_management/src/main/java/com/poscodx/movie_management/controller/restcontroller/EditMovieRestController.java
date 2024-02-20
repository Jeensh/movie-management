package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class EditMovieRestController implements RestController{

    private final MovieService movieService = new MovieService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int movieId = Integer.parseInt(paramMap.getOrDefault("movieId", "-1"));
        String title = paramMap.getOrDefault("title", "");
        int grade = Integer.parseInt(paramMap.getOrDefault("grade", "1"));
        String imageAddress = paramMap.getOrDefault("imageAddress", "");
        String description = paramMap.getOrDefault("description", "");

        MovieDTO movie = new MovieDTO();
        movie.setMovieId(movieId);
        movie.setTitle(title);
        movie.setGrade(grade);
        movie.setDescription(description);
        movie.setImageAddress(imageAddress);
        movieService.editMovie(movie);
    }
}

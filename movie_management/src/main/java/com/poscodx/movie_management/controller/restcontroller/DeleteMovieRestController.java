package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class DeleteMovieRestController implements RestController{

    private final MovieService movieService = new MovieService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int movieId = Integer.parseInt(paramMap.getOrDefault("movieId", "-1"));
        movieService.deleteMovie(movieId);
    }
}

package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class DeleteReviewRestController implements RestController{

    private final MovieService movieService = new MovieService();
    private final ReviewService reviewService = new ReviewService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int reviewId = Integer.parseInt(paramMap.getOrDefault("reviewId", "-1"));
        reviewService.deleteReview(reviewId);
    }
}

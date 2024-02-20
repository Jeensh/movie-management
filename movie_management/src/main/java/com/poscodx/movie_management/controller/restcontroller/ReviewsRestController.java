package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class ReviewsRestController implements RestController{

    private final MovieService movieService = new MovieService();
    private final ReviewService reviewService = new ReviewService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int movieId = Integer.parseInt(paramMap.getOrDefault("movieId", "-1"));
        MovieDTO movie = new MovieDTO();
        movie.setMovieId(movieId);
        String content = paramMap.getOrDefault("content", "");
        int score = Integer.parseInt(paramMap.getOrDefault("score", "0"));
        UserDTO user = (UserDTO) req.getSession().getAttribute("auth");

        if(user.getGrade() == UserGrade.NORMAL)
            content = "";

        if(movieId > 0){
            ReviewDTO review = new ReviewDTO();
            review.setMovie(movie);
            review.setWriter(user);
            review.setContent(content);
            review.setScore(score);
            reviewService.addReview(review);
        }
    }
}

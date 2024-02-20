package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import com.poscodx.movie_management.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class ScheduleRestController implements RestController{

    private final MovieService movieService = new MovieService();
    private final ReviewService reviewService = new ReviewService();
    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
//        int theaterId = Integer.parseInt(paramMap.getOrDefault("theaterId", "-1"));
//        TheaterDTO theater = new TheaterDTO();
//        theater.setTheaterId(theaterId);
//        String content = paramMap.getOrDefault("content", "");
//        String dat
//        int score = Integer.parseInt(paramMap.getOrDefault("score", "0"));
//        UserDTO user = (UserDTO) req.getSession().getAttribute("auth");
//
//        if(user.getGrade() == UserGrade.NORMAL)
//            content = "";
//
//        if(movieId > 0){
//            ReviewDTO review = new ReviewDTO();
//            review.setMovie(movie);
//            review.setWriter(user);
//            review.setContent(content);
//            review.setScore(score);
//            reviewService.addReview(review);
//        }
    }
}

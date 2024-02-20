package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ScheduleDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.Map;

public class AddScheduleRestController implements RestController{

    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int movieId = Integer.parseInt(paramMap.getOrDefault("movieId", "-1"));
        int theaterId = Integer.parseInt(paramMap.getOrDefault("theaterId", "-1"));
        Date startDate = Date.valueOf(paramMap.getOrDefault("startDate", null));
        Date endDate = Date.valueOf(paramMap.getOrDefault("endDate", null));

        ScheduleDTO schedule = new ScheduleDTO();
        TheaterDTO theater = new TheaterDTO();
        MovieDTO movie = new MovieDTO();
        theater.setTheaterId(theaterId);
        movie.setMovieId(movieId);
        schedule.setTheater(theater);
        schedule.setMovie(movie);
        schedule.setStartDate(startDate);
        schedule.setEndDate(endDate);
        scheduleService.addSchedule(schedule);
    }
}

package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import com.poscodx.movie_management.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class DeleteScheduleRestController implements RestController{

    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int scheduleId = Integer.parseInt(paramMap.getOrDefault("scheduleId", "-1"));
        scheduleService.deleteSchedule(scheduleId);
    }
}

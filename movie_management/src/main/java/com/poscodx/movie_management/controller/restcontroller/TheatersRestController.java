package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.*;
import com.poscodx.movie_management.repository.ScheduleRepository;
import com.poscodx.movie_management.repository.TheaterRepository;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import com.poscodx.movie_management.service.ScheduleService;
import com.poscodx.movie_management.service.TheaterService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class TheatersRestController implements RestController{

    private final TheaterService theaterService = new TheaterService();
    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int theaterId = Integer.parseInt(paramMap.getOrDefault("theaterId", "-1"));
        int schedulePageNumber = Integer.parseInt(paramMap.getOrDefault("schedulePageNumber", "-1"));
        int pageNumber = Integer.parseInt(paramMap.getOrDefault("pageNumber", "-1"));
        if(theaterId > 0){
            UserDTO user = (UserDTO) req.getSession().getAttribute("auth");

            TheaterDTO theater = theaterService.getTheaterById(theaterId);
            List<ScheduleDTO> scheduleList = scheduleService.getSchedulesByTheaterId(theaterId, 5, schedulePageNumber, user);
            theater.setScheduleList(scheduleList);
            int total = scheduleService.getScheduleCountByTheaterId(theaterId, user);
            model.put("theater", theater);
            model.put("total", total);
        }
        if(pageNumber > 0){
            List<TheaterDTO> list = theaterService.getTheatersByPageNumber(pageNumber);
            model.put("theaters", list);
        }
        if(pageNumber == 0){
            int total = theaterService.getTotalCount();
            model.put("total", total);
        }
    }
}

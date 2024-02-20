package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.TheaterService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class DeleteTheaterRestController implements RestController{

    private final TheaterService theaterService = new TheaterService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int theaterId = Integer.parseInt(paramMap.getOrDefault("theaterId", "-1"));
        theaterService.deleteTheater(theaterId);
    }
}

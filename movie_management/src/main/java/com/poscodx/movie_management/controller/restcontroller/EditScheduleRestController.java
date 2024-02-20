package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.ScheduleDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.service.ScheduleService;
import com.poscodx.movie_management.service.TheaterService;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.Map;

public class EditScheduleRestController implements RestController{

    private final ScheduleService scheduleService = new ScheduleService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int scheduleId = Integer.parseInt(paramMap.getOrDefault("scheduleId", "-1"));
        Date startDate = Date.valueOf(paramMap.getOrDefault("startDate", null));
        Date endDate = Date.valueOf(paramMap.getOrDefault("endDate", null));

        ScheduleDTO schedule = new ScheduleDTO();
        schedule.setScheduleId(scheduleId);
        schedule.setStartDate(startDate);
        schedule.setEndDate(endDate);
        scheduleService.editSchedule(schedule);
    }
}

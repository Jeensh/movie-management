package com.poscodx.movie_management.model;

import lombok.Data;

import java.sql.Date;


@Data
public class ScheduleDTO {
    private Integer scheduleId;
    private MovieDTO movie;
    private TheaterDTO theater;
    private Date startDate;
    private Date endDate;

    public ScheduleDTO() {}
}

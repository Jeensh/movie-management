package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.model.*;
import com.poscodx.movie_management.util.DbConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ScheduleRepository {
    private static DbConnectionUtil dbConnectionUtil = new DbConnectionUtil();

    // 스케줄 추가
    public void add(ScheduleDTO schedule){
        String query = "INSERT INTO schedule(movie_id, theater_id, start_date, end_date) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, schedule.getMovie().getMovieId());
            pstmt.setInt(2, schedule.getTheater().getTheaterId());
            pstmt.setDate(3, schedule.getStartDate());
            pstmt.setDate(4, schedule.getEndDate());

            System.out.println(schedule);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 총 스케줄 수 조회
    public int findScheduleCountByTheaterId(int theaterId){
        String query = "SELECT COUNT(*) AS total " +
                "FROM schedule " +
                "WHERE theater_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total = 0;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, theaterId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return total;
    }

    // 스케줄 극장 아이디로 범위 조회
    // 단, 현재 상영 중인 영화만 조회
    public List<ScheduleDTO> findByTheaterIdAndRange(int theaterId, int size, int pageNumber){
        String query = "SELECT * " +
                "FROM schedule " +
                "INNER JOIN movie ON movie.movie_id = schedule.movie_id " +
                "WHERE theater_id = ? " +
                "AND CURDATE() BETWEEN start_date AND end_date " +
                "ORDER BY schedule_id DESC " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ScheduleDTO> scheduleList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setInt(1, theaterId);
            pstmt.setInt(2, size);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                ScheduleDTO schedule = new ScheduleDTO();

                int movieId = rs.getInt("movie_id");
                TheaterDTO theater = new TheaterDTO();
                MovieDTO movie = new MovieDTO();
                theater.setTheaterId(theaterId);
                movie.setMovieId(movieId);
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));
                movie.setImageAddress(rs.getString("image_address"));

                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setTheater(theater);
                schedule.setMovie(movie);
                schedule.setStartDate(rs.getDate("start_date"));
                schedule.setEndDate(rs.getDate("end_date"));

                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return scheduleList;
    }

    // 스케줄 수정
    public void update(ScheduleDTO schedule){
        String query = "UPDATE schedule " +
                "SET start_date = ?, end_date = ? " +
                "WHERE schedule_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, schedule.getStartDate());
            pstmt.setDate(2, schedule.getEndDate());
            pstmt.setInt(3, schedule.getScheduleId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 스케줄 삭제
    public void deleteById(int scheduleId){
        String query = "DELETE FROM schedule WHERE schedule_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, scheduleId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 커넥션 받기 & 반납
    private Connection getConnection(){
        return dbConnectionUtil.getConnection();
    }

    private void closeConnection(Connection connection, PreparedStatement pstmt, ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

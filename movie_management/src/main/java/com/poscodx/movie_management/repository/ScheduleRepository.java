package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ScheduleRepository {
    private DataSource dataSource;
    public ScheduleRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

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

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 스케줄 극장 아이디로 범위 조회
    public List<ScheduleDTO> findByTheaterIdAndRange(int theaterId, int size, int pageNumber){
        String query = "SELECT * FROM schedule " +
                "WHERE theater_id = ?" +
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
        String query = "DELETE FROM schedule WHERE scheduleId = ?";

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
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

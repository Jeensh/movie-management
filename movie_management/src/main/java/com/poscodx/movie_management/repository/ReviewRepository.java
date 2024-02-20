package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.util.DbConnectionUtil;

import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ReviewRepository {
    private static DbConnectionUtil dbConnectionUtil = new DbConnectionUtil();

    // 리뷰 추가
    public void add(ReviewDTO review){
        String query = "INSERT INTO review(writer_id, movie_id, score, content) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, review.getWriter().getUserId());
            pstmt.setInt(2, review.getMovie().getMovieId());
            pstmt.setInt(3, review.getScore());
            pstmt.setString(4, review.getContent());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 영화 id로 평균 리뷰점수 구하기
    public String getAvgScoreByMovieId(int movieId){
        String query = "select CAST(round(avg(score), 1) AS CHAR) as score " +
                "FROM review " +
                "WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String avg = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                avg = rs.getString("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return avg;
    }

    // 총 리뷰 수 조회
    public int findReviewCountByMovieId(int movieId){
        String query = "SELECT COUNT(*) AS total " +
                "FROM review " +
                "WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total = 0;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

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

    // 총 리뷰 수 조회(작성자별)
    public int findReviewCountByMovieId(int movieId, int reviewType){
        String query = "SELECT COUNT(*) AS total " +
                "FROM review " +
                "INNER JOIN user ON review.writer_id = user.user_id " +
                "WHERE movie_id = ? AND " +
                "user.grade = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total = 0;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            int userGrade = reviewType - 1;
            pstmt.setInt(2, userGrade);

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

    // 전체 리뷰 목록 조회(영화 id로)
    public List<ReviewDTO> findByMovieIdAndRange(int movieId, int size, int pageNumber){
        String query = "SELECT * " +
                "FROM review " +
                "INNER JOIN user ON review.writer_id = user.user_id " +
                "WHERE movie_id = ? " +
                "ORDER BY review_id DESC " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ReviewDTO> reviewList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, size);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                ReviewDTO review = new ReviewDTO();

                review.setReviewId(rs.getInt("review_id"));
                int writerId = rs.getInt("writer_id");
                UserDTO writer = new UserDTO();
                MovieDTO movie = new MovieDTO();
                writer.setUserId(writerId);
                writer.setNickname(rs.getString("nickname"));
                writer.setGrade(rs.getInt("grade"));
                movie.setMovieId(movieId);

                review.setWriter(writer);
                review.setMovie(movie);
                review.setContent(rs.getString("content"));
                review.setScore(rs.getInt("score"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return reviewList;
    }

    // 리뷰 목록 조회(영화 id, 리뷰타입으로)
    public List<ReviewDTO> findByMovieIdAndRangeAndReviewType(int movieId, int size, int pageNumber, int reviewType){
        String query = "SELECT * " +
                "FROM review " +
                "INNER JOIN user ON review.writer_id = user.user_id " +
                "WHERE movie_id = ? AND " +
                "user.grade = ? " +
                "ORDER BY review_id DESC " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<ReviewDTO> reviewList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setInt(1, movieId);

            int userGrade = reviewType - 1;

            pstmt.setInt(2, userGrade);
            pstmt.setInt(3, size);
            pstmt.setInt(4, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                ReviewDTO review = new ReviewDTO();

                review.setReviewId(rs.getInt("review_id"));
                int writerId = rs.getInt("writer_id");
                UserDTO writer = new UserDTO();
                MovieDTO movie = new MovieDTO();
                writer.setUserId(writerId);
                writer.setNickname(rs.getString("nickname"));
                writer.setGrade(rs.getInt("grade"));
                movie.setMovieId(movieId);

                review.setWriter(writer);
                review.setMovie(movie);
                review.setContent(rs.getString("content"));
                review.setScore(rs.getInt("score"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return reviewList;
    }

    // 리뷰 수정
    public void update(ReviewDTO review){
        String query = "UPDATE review " +
                "SET score = ?, content = ? " +
                "WHERE review_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, review.getScore());
            pstmt.setString(2, review.getContent());
            pstmt.setInt(3, review.getReviewId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 리뷰 삭제
    public void deleteById(int reviewId){
        String query = "DELETE FROM review WHERE review_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, reviewId);

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

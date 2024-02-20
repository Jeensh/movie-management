package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.util.DbConnectionUtil;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MovieRepository {
    private static DbConnectionUtil dbConnectionUtil = new DbConnectionUtil();

    // 영화 등록
    public void add(MovieDTO movie){
        String query = "INSERT INTO movie(title, description, grade, thumbnail) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setInt(3, movie.getGrade().getValue());

            // 썸네일 이미지 전송
            byte[] thumbnail = movie.getThumbnail();
            if (movie.getThumbnail() != null) {
                pstmt.setBytes(4, thumbnail);
            }
            else{
                pstmt.setNull(4, Types.BLOB);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 영화 추가(썸네일 파일 대신, 이미지 경로로 추가)
    public void addWithAddress(MovieDTO movie){
        String query = "INSERT INTO movie(title, description, grade, image_address) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setInt(3, movie.getGrade().getValue());
            pstmt.setString(4, movie.getImageAddress());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 총 영화 수 조회
    public int findMovieCount(){
        String query = "SELECT COUNT(*) AS total FROM movie";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total = 0;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

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

    // 영화 단건 조회
    public MovieDTO findById(int movieId){
        String query = "SELECT * FROM movie WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        MovieDTO movie = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                movie = new MovieDTO();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setDescription(rs.getString("description"));
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));
                movie.setImageAddress(rs.getString("image_address"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                if(imageData != null) {
                    movie.setThumbnail(imageData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return movie;
    }

    // 영화 범위 조회
    public List<MovieDTO> findByRange(int size, int pageNumber){
        String query = "SELECT movie.movie_id, description, title, grade, image_address, thumbnail, " +
                "CAST(ROUND(AVG(review.score), 1) AS CHAR) AS score " +
                "FROM movie " +
                "LEFT JOIN review ON movie.movie_id = review.movie_id " +
                "GROUP BY movie.movie_id " +
                "ORDER BY movie.movie_id DESC " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<MovieDTO> movieList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                MovieDTO movie = new MovieDTO();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setDescription(rs.getString("description"));
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));
                movie.setImageAddress(rs.getString("image_address"));

                String avgScore = rs.getString("score");
                if(avgScore == null)
                    avgScore = "0";

                movie.setAvgScore(avgScore);

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                if(imageData != null) {
                    movie.setThumbnail(imageData);
                }

                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return movieList;
    }

    // 영화 키워드 조회
    public List<MovieDTO> findByKeyWord(String keyWord, int size, int pageNumber){
        String query = "SELECT * FROM movie " +
                "WHERE title LIKE ? " +
                "ORDER BY title " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<MovieDTO> movieList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setInt(2, size);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                MovieDTO movie = new MovieDTO();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setDescription(rs.getString("description"));
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));
                movie.setImageAddress(rs.getString("image_address"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                if(imageData != null) {
                    movie.setThumbnail(imageData);
                }

                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return movieList;
    }

    // 영화 수정
    public void update(MovieDTO movie){
        String query = "UPDATE movie " +
                "SET title = ?, description = ?, grade = ?, thumbnail = ?, image_address = ?" +
                "WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setInt(3, movie.getGrade().getValue());

            // 썸네일 이미지 전송
            byte[] thumbnail = movie.getThumbnail();
            if (movie.getThumbnail() != null) {
                pstmt.setBytes(4, thumbnail);
            }
            else{
                pstmt.setNull(4, Types.BLOB);
            }

            pstmt.setString(5, movie.getImageAddress());
            pstmt.setInt(6, movie.getMovieId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    public void updateWithoutThumbnail(MovieDTO movie){
        String query = "UPDATE movie " +
                "SET title = ?, description = ?, grade = ?, image_address " +
                "WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getDescription());
            pstmt.setInt(3, movie.getGrade().getValue());
            pstmt.setString(4, movie.getImageAddress());
            pstmt.setInt(5, movie.getMovieId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 영화 삭제
    public void deleteById(int movieId){
        String query = "DELETE FROM movie WHERE movie_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, movieId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 썸네일 이미지 관련
    private static byte[] convertImageToByteArray(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

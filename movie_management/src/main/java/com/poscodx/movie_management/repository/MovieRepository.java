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
            byte[] imageData = null;
            BufferedImage Thumbnail = movie.getThumbnail();
            if (movie.getThumbnail() != null) {
                imageData = convertImageToByteArray(Thumbnail);
                pstmt.setBytes(4, imageData);
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

    // 영화 단건 조회
    public MovieDTO findById(int movieId){
        String query = "SELECT * FROM moive WHERE movie_id = ?";

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
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setDescription(rs.getString("description"));
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    movie.setThumbnail(ImageIO.read(bis));
                } catch (IOException e) {
                    e.printStackTrace();
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
        String query = "SELECT * FROM moive " +
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
            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                MovieDTO movie = new MovieDTO();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setDescription(rs.getString("description"));
                movie.setTitle(rs.getString("title"));
                movie.setGrade(rs.getInt("grade"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    movie.setThumbnail(ImageIO.read(bis));
                } catch (IOException e) {
                    e.printStackTrace();
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
        String query = "SELECT * FROM moive " +
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

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    movie.setThumbnail(ImageIO.read(bis));
                } catch (IOException e) {
                    e.printStackTrace();
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
                "SET title = ?, description = ?, grade = ?, thumbnail = ? " +
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
            byte[] imageData = null;
            BufferedImage Thumbnail = movie.getThumbnail();
            if (movie.getThumbnail() != null) {
                imageData = convertImageToByteArray(Thumbnail);
                pstmt.setBytes(4, imageData);
            }
            else{
                pstmt.setNull(4, Types.BLOB);
            }

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

package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.TheaterDTO;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TheaterRepository {
    private DataSource dataSource;
    public TheaterRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    // 극장 추가
    public void add(TheaterDTO theater){
        String query = "INSERT INTO theater(name, location, tel, thumbnail) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, theater.getName());
            pstmt.setString(2, theater.getLocation());
            pstmt.setString(3, theater.getTel());

            // 썸네일 이미지 전송
            byte[] imageData = null;
            BufferedImage Thumbnail = theater.getThumbnail();
            if (Thumbnail != null) {
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

    // 극장 단건 조회
    public TheaterDTO findById(int theaterId){
        String query = "SELECT * FROM theater WHERE theater_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        TheaterDTO theater = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, theaterId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                theater.setTheaterId(rs.getInt("theater_id"));
                theater.setName(rs.getString("name"));
                theater.setLocation(rs.getString("location"));
                theater.setTel(rs.getString("tel"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    theater.setThumbnail(ImageIO.read(bis));
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
        return theater;
    }

    // 극장 범위 조회
    public List<TheaterDTO> findByRange(int size, int pageNumber){
        String query = "SELECT * FROM theater " +
                "ORDER BY name " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<TheaterDTO> theaterList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                TheaterDTO theater = new TheaterDTO();
                theater.setTheaterId(rs.getInt("theater_id"));
                theater.setName(rs.getString("name"));
                theater.setLocation(rs.getString("location"));
                theater.setTel(rs.getString("tel"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    theater.setThumbnail(ImageIO.read(bis));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                theaterList.add(theater);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return theaterList;
    }

    // 극장 키워드 조회
    public List<TheaterDTO> findByKeyWord(String keyWord, int size, int pageNumber){
        String query = "SELECT * FROM theater " +
                "WHERE name LIKE ? " +
                "ORDER BY name " +
                "LIMIT ? OFFSET ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<TheaterDTO> theaterList = new LinkedList<>();

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);

            int offset = (pageNumber - 1) * size;
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setInt(2, size);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while(rs.next()){
                TheaterDTO theater = new TheaterDTO();
                theater.setTheaterId(rs.getInt("theater_id"));
                theater.setName(rs.getString("name"));
                theater.setLocation(rs.getString("location"));
                theater.setTel(rs.getString("tel"));

                // 썸네일 이미지 받아오기
                byte[] imageData = rs.getBytes("thumbnail");

                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    theater.setThumbnail(ImageIO.read(bis));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                theaterList.add(theater);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return theaterList;
    }

    // 극장 수정
    public void update(TheaterDTO theater){
        String query = "UPDATE theater " +
                "SET name = ?, location = ?, tel = ?, thumbnail = ? " +
                "WHERE theater_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, theater.getName());
            pstmt.setString(2, theater.getLocation());
            pstmt.setString(3, theater.getTel());

            // 썸네일 이미지 전송
            byte[] imageData = null;
            BufferedImage Thumbnail = theater.getThumbnail();
            if (theater.getThumbnail() != null) {
                imageData = convertImageToByteArray(Thumbnail);
                pstmt.setBytes(4, imageData);
            }
            else{
                pstmt.setNull(4, Types.BLOB);
            }

            pstmt.setInt(5, theater.getTheaterId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 극장 삭제
    public void deleteById(int theaterId){
        String query = "DELETE FROM theater WHERE theater_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, theaterId);

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

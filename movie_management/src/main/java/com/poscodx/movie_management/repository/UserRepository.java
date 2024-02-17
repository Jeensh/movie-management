package com.poscodx.movie_management.repository;

import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.util.DbConnectionUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static DbConnectionUtil dbConnectionUtil = new DbConnectionUtil();

    // 유저 추가
    public void add(UserDTO user){
        String query = "INSERT INTO user(username, password, nickname, grade) " +
                "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setInt(4, user.getGrade().getValue());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 유저 조회
    public UserDTO findById(int userId){
        String query = "SELECT * FROM user WHERE user_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserDTO user = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();

            while(rs.next()){
                user.setUserId(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setGrade(rs.getInt("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return user;
    }

    // 패스워드와 아이디로 조회(로그인 인증)
    public UserDTO findByUsernameAndPassword(String username, String password){
        String query = "SELECT * FROM user WHERE username = ? and password = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserDTO user = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            while(rs.next()){
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setGrade(rs.getInt("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
        return user;
    }

    // 유저 삭제
    public void deleteById(int userId){
        String query = "DELETE FROM user WHERE user_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserDTO user = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(connection, pstmt, rs);
        }
    }

    // 유저 수정

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

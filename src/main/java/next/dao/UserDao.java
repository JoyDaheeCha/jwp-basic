package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate() {
            @Override
            void setValues(User user, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }
        };
        template.update(user);
    }


    public void update(User user) throws SQLException {

        JdbcTemplate template = new JdbcTemplate() {
            @Override
            void setValues(User user, PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            String createQuery() {
                return "UPDATE USERS SET password=?,name=?,email=? WHERE userId=?";
            }
        };
        template.update(user);
    }

    public List<User> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForSelectAll();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            return mapRows(rs);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    private List<User> mapRows(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        if (rs.next()) {
            users.add(new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")));
        }

        return users;
    }

    private String createQueryForSelectAll() {
        String sql = "SELECT userId, password, name, email FROM USERS";
        return sql;
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForSelectOne();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            return mapRow(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = null;
        if (rs.next()) {
            user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
        }

        return user;
    }

    private String createQueryForSelectOne() {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return sql;
    }
}

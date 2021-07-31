package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate insertTmplt = new JdbcTemplate();

        PreparedStatementSetter psSetter = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };
        insertTmplt.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", psSetter);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate updateTmplt = new JdbcTemplate();
        PreparedStatementSetter psSetter = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };
        updateTmplt.update("UPDATE USERS SET password=?,name=?,email=? WHERE userId=?",  psSetter);
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate selectTmplt = new JdbcTemplate();

        RowMapper rowMapper = rs -> {
            List<User> users = new ArrayList<>();
            if (rs.next()) {
                users.add(new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));
            }
            return users;
        };

        return (List<User>) selectTmplt.query("SELECT userId, password, name, email FROM USERS", rowMapper);
    }


    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate selectTmplt = new JdbcTemplate();

        PreparedStatementSetter psSetter = pstmt -> {
            pstmt.setString(1, userId);
        };

        RowMapper rowMapper = rs -> {
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;
        };

        return (User) selectTmplt.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", psSetter, rowMapper);
    }
}

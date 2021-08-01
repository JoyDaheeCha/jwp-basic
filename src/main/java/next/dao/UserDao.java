package next.dao;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) {

        JdbcTemplate template = new JdbcTemplate() {};
        template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }


    public void update(User user) {

        JdbcTemplate template = new JdbcTemplate();
        template.update("UPDATE USERS SET password=?,name=?,email=? WHERE userId=?", pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public List<User> findAll() {
        JdbcTemplate template = new JdbcTemplate();
        return template.query("SELECT userId, password, name, email FROM USERS",
                rs -> new User(rs.getString("userId"),
                               rs.getString("password"),
                               rs.getString("name"),
                               rs.getString("email")));
    }

    public User findByUserId(String userId) {
        JdbcTemplate template = new JdbcTemplate();
        return template.queryForObject(
                "SELECT userId, password, name, email FROM USERS WHERE userid=?",
                pstmt -> {
                    pstmt.setString(1, userId);
                },
                rs ->  new User(rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")));
    }
}

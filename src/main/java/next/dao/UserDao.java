package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }


    public void update(User user) throws SQLException {

        JdbcTemplate template = new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        template.update("UPDATE USERS SET password=?,name=?,email=? WHERE userId=?");
    }

    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate template = new SelectJdbcTemplate() {
            @Override
            void setValue(PreparedStatement pstmt) {

            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                List<User> users = new ArrayList<>();
                if (rs.next()) {
                    users.add(new User(rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email")));
                }

                return users;
            }

            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS";
            }
        };
        return (List<User>) template.query();
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate template = new SelectJdbcTemplate() {
            @Override
            void setValue(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email"));
                }

                return user;
            }

            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            }
        };
        return (User) template.queryForObject();
    }
}

package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate insertTmplt = new JdbcTemplate(){

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                // 불필요
                return new Object();
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        insertTmplt.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) throws SQLException {
        JdbcTemplate updateTmplt = new JdbcTemplate(){

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                // 불필요
                return new Object();
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };
        updateTmplt.update("UPDATE USERS SET password=?,name=?,email=? WHERE userId=?");
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate selectTmplt = new JdbcTemplate() {
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
            void setValues(PreparedStatement pstmt) throws SQLException {
                // find All 에 대한 함수는 적을게 없는데..
            }
        };
        return (List<User>) selectTmplt.query("SELECT userId, password, name, email FROM USERS");
    }


    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate selectTmplt = new JdbcTemplate() {
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
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return (User) selectTmplt.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?");
    }
}

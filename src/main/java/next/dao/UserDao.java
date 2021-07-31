package next.dao;

import next.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) {
        JdbcTemplate insertTmplt = new JdbcTemplate();

        PreparedStatementSetter psSetter = pstmt -> {
            try {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            } catch(SQLException e) {
                throw new DataAccessException(e);
            }
        };
        insertTmplt.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", psSetter);
    }

    public void update(User user) {
        JdbcTemplate updateTmplt = new JdbcTemplate();
        PreparedStatementSetter psSetter = pstmt -> {
            try {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            } catch(SQLException e) {
                throw new DataAccessException(e);
            }
        };
        updateTmplt.update("UPDATE USERS SET password=?,name=?,email=? WHERE userId=?",  psSetter);
    }

    public List<User> findAll() {
        JdbcTemplate selectTmplt = new JdbcTemplate();

        RowMapper rowMapper = rs -> {
            List<User> users = new ArrayList<>();
            try {
                if (rs.next()) {
                    users.add(new User(rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email")));
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
            return users;
        };

        return (List<User>) selectTmplt.query("SELECT userId, password, name, email FROM USERS", rowMapper);
    }


    public User findByUserId(String userId) {
        JdbcTemplate selectTmplt = new JdbcTemplate();

        PreparedStatementSetter psSetter = pstmt -> {
            try {
                pstmt.setString(1, userId);
            } catch (SQLException e){
                throw new DataAccessException(e);
            }
        };

        RowMapper rowMapper = rs -> {
            User user = null;
            try {
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email"));
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
            return user;
        };

        return (User) selectTmplt.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", psSetter, rowMapper);
    }
}

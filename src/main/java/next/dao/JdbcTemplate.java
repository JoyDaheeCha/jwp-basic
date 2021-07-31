package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public Object query(String sql, RowMapper rowMapper) {
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return rowMapper.mapRow(rs);

        } catch (SQLException e) {
            throw new DataAccessException(e);

        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter psSetter, RowMapper rowMapper){
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            psSetter.values(pstmt);
            ResultSet rs = pstmt.executeQuery();
            return rowMapper.mapRow(rs);

        } catch (SQLException e) {
            throw new DataAccessException(e);

        }
    }

    public void update(String sql, PreparedStatementSetter psSetter){
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            psSetter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);

        }
    }
}

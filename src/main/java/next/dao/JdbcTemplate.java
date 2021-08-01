package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pss) throws SQLException {
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Object query(String sql, RowMapper rm) throws SQLException {

        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            return rm.mapRow(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter pss, RowMapper rm) throws SQLException {
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            ResultSet rs = pstmt.executeQuery();

            return rm.mapRow(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}

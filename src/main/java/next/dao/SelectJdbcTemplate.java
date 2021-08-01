package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SelectJdbcTemplate {

    public Object query(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            return mapRow(rs);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public Object queryForObject(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);

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

    abstract void setValues(PreparedStatement pstmt) throws SQLException;

    abstract Object mapRow(ResultSet rs) throws SQLException;
}

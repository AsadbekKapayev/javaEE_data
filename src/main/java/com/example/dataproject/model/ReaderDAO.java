package com.example.dataproject.model;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    private static final String GET_USER = "SELECT * FROM Userr WHERE username=?";
    private static final String CREATE_READER = "INSERT INTO Reader VALUES (?, ?, ?, ?)";
    private static final String CREATE_USER = "INSERT INTO Userr VALUES (?, ?, ?, ?)";

    private static final String GET_NEXT_ID = "SELECT id FROM ObjectIDs WHERE table_name=?";
    private static final String UPDATE_ID = "UPDATE ObjectIDs SET id=? WHERE table_name=?";
    private static final String CREATE_ID = "INSERT INTO ObjectIDs (table_name, id) VALUES (?, ?)";


    private DataSource dataSource;

    public ReaderDAO() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup( "java:/comp/env/jdbc/postgres" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username) {
        User user = new User();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(GET_USER);
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setReader_id(rs.getInt("reader_id"));

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return user;
    }

    public void addReader(Reader reader, User user) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            int id = getNextID("reader");
            con = dataSource.getConnection();
            ps = con.prepareStatement(CREATE_READER);
            ps.setInt(1, id);
            ps.setString(2, reader.getPhoneNumber());
            ps.setString(3, reader.getFullName());
            ps.setString(4, reader.getAddress());
            ps.executeUpdate();
            int userId = getNextID("userr");
            ps = con.prepareStatement(CREATE_USER);
            ps.setInt(1, userId);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(ps != null) {
                try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public int getNextID(String tableName) {
        int id = 1;
        Connection con = null;
        PreparedStatement queryStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            queryStmt = con.prepareStatement(GET_NEXT_ID);
            queryStmt.setString(1, tableName);
            rs = queryStmt.executeQuery();
            if(rs.next()) {
                id = rs.getInt("id");
                updateStmt = con.prepareStatement(UPDATE_ID);
                updateStmt.setInt(1, id+1);
                updateStmt.setString(2, tableName);
                updateStmt.executeUpdate();
            } else {
                updateStmt = con.prepareStatement(CREATE_ID);
                updateStmt.setString(1, tableName);
                updateStmt.setInt(2, id+1);
                updateStmt.executeUpdate();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(queryStmt != null) {
                try { queryStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(updateStmt != null) {
                try { updateStmt.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if(con != null) {
                try { con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }

        return id;
    }
}

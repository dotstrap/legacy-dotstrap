/**
 * UserDAO.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
import java.util.ArrayList;

import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAO.
 */
public class UserDAO {
    
    /** The db. */
    private Database db;
    
    /**
     * Instantiates a new user dao.
     *
     * @param db the db
     */
    public UserDAO(Database db) {
        this.db = db;
    }
    
    /**
     * Adds the.
     *
     * @param user the user
     * @return the int
     */
    public int add(User user) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO User (username, password, FirstName, LastName, Email, RecordCount, CurrentBatch)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setString(1, user.getCreds().getUsername());
            SQLstmt.setString(2, user.getCreds().getPassword());
            SQLstmt.setString(3, user.getUserInfo().getFirstName());
            SQLstmt.setString(4, user.getUserInfo().getLastName());
            SQLstmt.setString(5, user.getUserInfo().getEmail());
            SQLstmt.setInt(6, user.getRecordCount());
            SQLstmt.setInt(7, user.getCurrentBatch());
            
            if (SQLstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                user.setID(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }
    
    /**
     * Delete.
     *
     * @param user the user
     */
    public void delete(User user) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from User WHERE username = ? AND password = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setString(1, user.getCreds().getUsername());
            SQLstmt.setString(2, user.getCreds().getPassword());
            
            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
    }
    
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from User";
            SQLstmt = connection.prepareStatement(selectsql);
            
            resultset = SQLstmt.executeQuery();
            
            while (resultset.next()) {
                User returnUser = new User();
                UserInfo userInfo = new UserInfo();
                returnUser.setID(resultset.getInt(1));
                Credentials creds = new Credentials(
                        resultset.getString(2), resultset.getString(3));
                returnUser.setCreds(creds);
                userInfo.setFirstName(resultset.getString(4));
                userInfo.setLastName(resultset.getString(5));
                userInfo.setEmail(resultset.getString(6));
                returnUser.setUserInfo(userInfo);
                returnUser.setRecordCount(resultset.getInt(7));
                returnUser.setCurrentBatch(resultset.getInt(8));
                users.add(returnUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
        resultset = null;
        
        return users;
    }
    
    /**
     * Gets the user.
     *
     * @param creds the creds
     * @return the user
     */
    public User getUser(Credentials creds) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        User returnUser = new User();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from User WHERE username = ? AND password = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setString(1, creds.getUsername());
            SQLstmt.setString(2, creds.getPassword());
            
            resultset = SQLstmt.executeQuery();
            
            resultset.next();
            returnUser.setCreds(creds);
            UserInfo userInfo = new UserInfo();
            returnUser.setID(resultset.getInt(1));
            userInfo.setFirstName(resultset.getString(4));
            userInfo.setLastName(resultset.getString(5));
            userInfo.setEmail(resultset.getString(6));
            returnUser.setUserInfo(userInfo);
            returnUser.setRecordCount(resultset.getInt(7));
            returnUser.setCurrentBatch(resultset.getInt(8));
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        connection = null;
        SQLstmt = null;
        resultset = null;
        return returnUser;
    }
    
    /**
     * Update.
     *
     * @param user the user
     */
    public void update(User user) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "UPDATE user SET FirstName = ?, LastName = ?,"
                    + "Email = ?, RecordCount = ?, CurrentBatch = ?"
                    + "WHERE username = ? AND password = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setString(1, user.getUserInfo().getFirstName());
            SQLstmt.setString(2, user.getUserInfo().getLastName());
            SQLstmt.setString(3, user.getUserInfo().getEmail());
            SQLstmt.setInt(4, user.getRecordCount());
            SQLstmt.setInt(5, user.getCurrentBatch());
            SQLstmt.setString(6, user.getCreds().getUsername());
            SQLstmt.setString(7, user.getCreds().getPassword());
            
            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
    }
    
    /**
     * Validate user.
     *
     * @param creds the creds
     * @return true, if successful
     */
    public boolean validateUser(Credentials creds) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        boolean validate = false;
        try {
            connection = db.getConnection();
            String insertsql = "SELECT * from User"
                    + "WHERE username = ? AND password = ?";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setString(1, creds.getUsername());
            SQLstmt.setString(2, creds.getPassword());
            
            if (SQLstmt.executeUpdate() == 1) {
                validate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
        
        return validate;
    }
}

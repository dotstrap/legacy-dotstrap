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
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.model.User;

/**
 * The Class UserDAO.
 * Interfaces with the database to CRUD users & getAll() users.
 */
public class UserDAO {

    /** The db. */
    private Database      db;
    private static Logger logger;

    /**
     * Instantiates a new user dao.
     *
     * @param db the db
     */
    public UserDAO(Database db) {
        logger = Logger.getLogger("server");
        this.db = db;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> allUsers = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();

            String selectsql = "SELECT * from User";

            SQLstmt = connection.prepareStatement(selectsql);
            resultset = SQLstmt.executeQuery();

            while (resultset.next()) {
                User resultUser = new User();

                resultUser.setID(resultset.getInt(1));
                resultUser.setUsername(resultset.getString(2));
                resultUser.setPassword(resultset.getString(3));

                resultUser.setFirst(resultset.getString(4));
                resultUser.setLast(resultset.getString(5));
                resultUser.setEmail(resultset.getString(6));

                resultUser.setRecordCount(resultset.getInt(7));
                resultUser.setCurrBatch(resultset.getInt(8));

                allUsers.add(resultUser);
                SQLstmt = connection.prepareStatement(selectsql);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        connection = null;
        SQLstmt = null;

        try {
            resultset.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            resultset = null;
        }
        return allUsers;
    }
    /**
     * Validate user.
     *
     * @param creds the creds
     * @return true, if successful
     */
    public boolean validateUser(User user) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        boolean validate = false;
        try {
            String username = user.getUsername();
            String password = user.getPassword();

            connection = db.getConnection();

            String insertsql = "SELECT * from User"
                    + "WHERE username = ? AND password = ?";

            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setString(1, username);
            SQLstmt.setString(2, password);

            if (SQLstmt.executeUpdate() == 1) {
                validate = true;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        connection = null;
        SQLstmt = null;

        return validate;
    }

    /**
     * Creates a new user.
     *
     * @param user the user
     * @return the int ID of the user
     */
    public int create(User user) {
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
            SQLstmt.setString(1, user.getUsername());
            SQLstmt.setString(2, user.getPassword());
            SQLstmt.setString(3, user.getFirst());
            SQLstmt.setString(4, user.getLast());
            SQLstmt.setString(5, user.getEmail());
            SQLstmt.setInt(6, user.getRecordCount());
            SQLstmt.setInt(7, user.getCurrBatch());

            if (SQLstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                user.setID(id);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        connection = null;
        SQLstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }

    /**
     * Gets the user with specified username and password
     *
     * @param username
     * @param password
     * @return true if valid, else false
     */
    public User read(String username, String password) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        User returnUser = new User();
        try {
            connection = db.getConnection();

            String selectsql = "SELECT * from User WHERE username = ? AND password = ?";

            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setString(1, username);
            SQLstmt.setString(2, password);

            resultset = SQLstmt.executeQuery();

            resultset.next();

            returnUser.setID(resultset.getInt(1));
            returnUser.setUsername(username);
            returnUser.setPassword(password);
            returnUser.setFirst(resultset.getString(4));
            returnUser.setLast(resultset.getString(5));
            returnUser.setEmail(resultset.getString(6));
            returnUser.setRecordCount(resultset.getInt(7));
            returnUser.setCurrBatch(resultset.getInt(8));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
            return null;
        }
        connection = null;
        SQLstmt = null;
        resultset = null;
        return returnUser;
    }

    /**
     * Updates the given user with the provided information.
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

            SQLstmt.setString(1, user.getFirst());
            SQLstmt.setString(2, user.getLast());
            SQLstmt.setString(3, user.getEmail());
            SQLstmt.setInt(4, user.getRecordCount());
            SQLstmt.setInt(5, user.getCurrBatch());

            SQLstmt.setString(6, user.getUsername());
            SQLstmt.setString(7, user.getPassword());

            SQLstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        connection = null;
        SQLstmt = null;
    }

    /**
     * Deletes the specified user.
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
            SQLstmt.setString(1, user.getUsername());
            SQLstmt.setString(2, user.getPassword());

            SQLstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        connection = null;
        SQLstmt = null;
    }
}

/**
 * UserDAO.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.model.User;

/**
 * The Class UserDAO. Interfaces with the database to CRUD users & getAll()
 * users.
 */
public class UserDAO {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
   }

    /** The db. */
    private Database      db;

    /**
     * Instantiates a new user dao.
     *
     * @param db
     *            the db
     */
    public UserDAO(Database db) {
        this.db = db;
    }

    public void initTable() throws DatabaseException {
        logger.entering("server.database.UserDAO", "initTable");

        Statement stmt1 = null;
        Statement stmt2 = null;

        String dropUserTable = "DROP TABLE IF EXISTS User";
        String createUserTable = "CREATE TABLE User ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "Username TEXT NOT NULL UNIQUE, "
                + "Password TEXT NOT NULL, "
                + "FirstName TEXT NOT NULL, "
                + "LastName TEXT NOT NULL, "
                + "Email TEXT NOT NULL UNIQUE, "
                + "RecordCount INTEGER NOT NULL, "
                + "CurrentBatchID INTEGER NOT NULL)";

        try {
            stmt1 = db.getConnection().createStatement();
            stmt1.executeUpdate(dropUserTable);

            stmt2 = db.getConnection().createStatement();
            stmt2.executeUpdate(createUserTable);
        }  catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(stmt1);
            Database.closeSafely(stmt2);
        }

        logger.exiting("server.database.FieldDAO", "initTable");
    }

    public ArrayList<User> getAll() throws DatabaseException {
        logger.entering("server.database.UserDAO", "getAll");

        ArrayList<User> allUsers = new ArrayList<User>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from User";
            pstmt = db.getConnection().prepareStatement(selectsql);
            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                User resultUser = new User();

                resultUser.setUserID(resultset.getInt("UserID"));
                resultUser.setUsername(resultset.getString("Username"));
                resultUser.setPassword(resultset.getString("Password"));

                resultUser.setFirst(resultset.getString("FirstName"));
                resultUser.setLast(resultset.getString("LastName"));
                resultUser.setEmail(resultset.getString("Email"));

                resultUser.setRecordCount(resultset.getInt("RecordCount"));
                resultUser.setCurrBatch(resultset.getInt("CurrentBatchID"));

                allUsers.add(resultUser);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.UserDAO", "getAll");
        return allUsers;
    }

    /**
     * Validate user.
     *
     * @param user
     *            - the user to validate
     * @return true, if successful
     */
    //public boolean validateUser(String username, String password) {
        //PreparedStatement pstmt = null;
        //boolean isValid = true;
        //try {
            //String selectsql = "SELECT * from User WHERE Username = ? AND Password = ?";
            //pstmt = db.getConnection().prepareStatement(selectsql);
            //pstmt.setString(1, username);
            //pstmt.setString(2, password);
            //pstmt.executeQuery();
        //} catch (SQLException e) {
            //isValid = false;
        //} finally {
            //Database.closeSafely(pstmt);
        //}
        //return isValid;
    //}

    /**
     * Creates a new user.
     *
     * @param newUser
     *            the user
     * @return the int UserID of the user
     */
    public int create(User newUser) throws DatabaseException {
        logger.entering("server.database.UserDAO", "create");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO User ("
                + "Username, Password, FirstName, LastName, Email, RecordCount, CurrentBatchID)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = db.getConnection().prepareStatement(insertsql);
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getFirst());
            pstmt.setString(4, newUser.getLast());
            pstmt.setString(5, newUser.getEmail());
            pstmt.setInt(6, newUser.getRecordCount());
            pstmt.setInt(7, newUser.getCurrBatch());

            if (pstmt.executeUpdate() == 1) {
                Statement stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int userid = resultset.getInt(1);
                newUser.setUserID(userid);
            } else {
                throw new DatabaseException("Unable to insert user into database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.UserDAO", "create");
        return newUser.getUserID();
    }

    /**
     * Gets the user with specified username and password
     *
     * @param username
     * @param password
     * @return true if valid, else false
     */
    public User read(String username, String password) throws DatabaseException {
        logger.entering("server.database.UserDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        User returnUser = new User();
        try {
            String selectsql = "SELECT * from User WHERE Username = ? AND Password = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnUser.setUserID(resultset.getInt(1));
            returnUser.setUsername(username);
            returnUser.setPassword(password);
            returnUser.setFirst(resultset.getString(4));
            returnUser.setLast(resultset.getString(5));
            returnUser.setEmail(resultset.getString(6));
            returnUser.setRecordCount(resultset.getInt(7));
            returnUser.setCurrBatch(resultset.getInt(8));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
            //return null;
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.UserDAO", "read");
        return returnUser;
    }

    /**
     * Updates the given user with the provided information.
     *
     * @param user
     *            the user
     */
    public void update(User user) throws DatabaseException {
        logger.entering("server.database.UserDAO", "update");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "UPDATE User SET FirstName = ?, LastName = ?,"
                    + "Email = ?, RecordCount = ?, CurrentBatchID = ?"
                    + "WHERE Username = ? AND Password = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setString(1, user.getFirst());
            pstmt.setString(2, user.getLast());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getRecordCount());
            pstmt.setInt(5, user.getCurrBatch());

            pstmt.setString(6, user.getUsername());
            pstmt.setString(7, user.getPassword());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.UserDAO", "update");
    }

    /**
     * Deletes the specified user.
     *
     * @param user
     *            the user
     */
    public void delete(User user) throws DatabaseException {
        logger.entering("server.database.UserDAO", "delete");

        // Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from User WHERE Username = ? AND Password = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.UserDAO", "delete");
    }
}

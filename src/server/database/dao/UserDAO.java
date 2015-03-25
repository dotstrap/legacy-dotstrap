/**
 * UserDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAO. Interfaces with the database to CRUD users & getAll() users.
 */
public class UserDAO {
  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger(Database.LOG_NAME);
  }

  /** The db. */
  private Database      db;

  /**
   * Instantiates a new user dao.
   *
   * @param db the db
   */
  public UserDAO(Database db) {
    this.db = db;
  }

  /**
   * Initializes the table.
   *
   * @throws DatabaseException the database exception
   */
  public void initTable() throws DatabaseException {
    Statement stmt1 = null;
    Statement stmt2 = null;
    // @formatter:off
    String dropUserTable = "DROP TABLE IF EXISTS User";
    String createUserTable =
        "CREATE TABLE User ("
            + "UserId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "Username TEXT NOT NULL UNIQUE, "
            + "Password TEXT NOT NULL, "
            + "FirstName TEXT NOT NULL, "
            + "LastName TEXT NOT NULL, "
            + "Email TEXT NOT NULL UNIQUE, "
            + "RecordCount INTEGER NOT NULL, "
            + "CurrentBatchId INTEGER NOT NULL)"; // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropUserTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createUserTable);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(stmt1);
      Database.closeSafely(stmt2);
    }
  }

  public ArrayList<User> getAll() throws DatabaseException {
    ArrayList<User> allUsers = new ArrayList<User>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query = "SELECT * from User";
      pstmt = db.getConnection().prepareStatement(query);
      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        User resultUser = new User();

        resultUser.setUserId(resultset.getInt("UserId"));
        resultUser.setUsername(resultset.getString("Username"));
        resultUser.setPassword(resultset.getString("Password"));

        resultUser.setFirst(resultset.getString("FirstName"));
        resultUser.setLast(resultset.getString("LastName"));
        resultUser.setEmail(resultset.getString("Email"));

        resultUser.setRecordCount(resultset.getInt("RecordCount"));
        resultUser.setCurrBatch(resultset.getInt("CurrentBatchId"));

        allUsers.add(resultUser);
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allUsers;
  }

  /**
   * Creates a new user.
   *
   * @param newUser the user
   * @return the int UserId of the user
   * @throws DatabaseException the database exception
   */
  public int create(User newUser) throws DatabaseException {
    PreparedStatement pstmt = null;  //@formatter:off
    ResultSet resultset = null;
    try {
      String query = "INSERT INTO User ("
          + "Username, Password, FirstName, LastName, "
          + "Email, RecordCount, CurrentBatchId)"
          + "VALUES (?, ?, ?, ?, ?, ?, ?)";  //@formatter:on
      pstmt = db.getConnection().prepareStatement(query);
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
        newUser.setUserId(userid);
      } else {
        throw new DatabaseException("Unable to insert user into database.");
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return newUser.getUserId();
  }

  /**
   * Gets the user with specified username and password.
   *
   * @param username the username
   * @param password the password
   * @return true if valid, else false
   * @throws DatabaseException the database exception
   */
  public User read(String username, String password) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    User returnUser = new User();
    try {
      String query = "SELECT * from User WHERE Username = ? AND Password = ?";
      pstmt = db.getConnection().prepareStatement(query);

      pstmt.setString(1, username);
      pstmt.setString(2, password);

      resultset = pstmt.executeQuery();

      resultset.next();
      returnUser.setUserId(resultset.getInt(1));
      returnUser.setUsername(username);
      returnUser.setPassword(password);
      returnUser.setFirst(resultset.getString(4));
      returnUser.setLast(resultset.getString(5));
      returnUser.setEmail(resultset.getString(6));
      returnUser.setRecordCount(resultset.getInt(7));
      returnUser.setCurrBatch(resultset.getInt(8));
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return returnUser;
  }

  /**
   * Updates the given user with the provided information.
   *
   * @param user the user
   * @throws DatabaseException the database exception
   */
  public void update(User user) throws DatabaseException {
    PreparedStatement pstmt = null;//@formatter:off
    try {
      String query = "UPDATE User SET FirstName = ?, LastName = ?, "
          + "Email = ?, RecordCount = ?, CurrentBatchId = ?"
          + "WHERE Username = ? AND Password = ?";  //@formatter:on
      pstmt = db.getConnection().prepareStatement(query);

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
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
    }
  }

  /**
   * Updates the id of the image a user is currently working on.
   *
   * @param userId the id of the user working on a new image
   * @param batchId the id of the image the user is working on
   * @throws DatabaseException the database exception
   */
  public void updateCurrentBatchId(int userId, int batchId) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;

    try {
      String query = "UPDATE User SET CurrentBatchId = ? WHERE UserId = ?";
      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, batchId);
      pstmt.setInt(2, userId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }

    return;
  }

  /**
   * Clears the current image id of a user.
   *
   * @param userId the user id
   * @throws DatabaseException the database exception
   */
  public void clearCurrentBatchId(int userId) throws DatabaseException {
    updateCurrentBatchId(userId, -1);
    return;
  }

  /**
   * Increments the number of records a user has indexed.
   *
   * @param userId the id of the user who indexed another record
   * @return true if operation succeeded, false otherwise
   * @throws DatabaseException the database exception
   */
  public void incrementIndexedRecords(int userId) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;

    try {
      String query = "UPDATE User SET IndexedRecords = IndexedRecords + 1 WHERE UserId = ?";
      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, userId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }

    return;
  }

  /**
   * Deletes the specified user.
   *
   * @param user the user
   * @throws DatabaseException the database exception
   */
  public void delete(User user) throws DatabaseException {
    PreparedStatement pstmt = null;
    try {
      String query = "DELETE from User WHERE Username = ? AND Password = ?";

      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setString(1, user.getUsername());
      pstmt.setString(2, user.getPassword());

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
    }
  }
}

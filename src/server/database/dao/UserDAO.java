/**
 * UserDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
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
  private final Database db;

  /**
   * Instantiates a new user dao.
   *
   * @param db the db
   */
  public UserDAO(Database db) {
    this.db = db;
  }

  public void initTable() throws DatabaseException {
    Statement stmt1 = null;
    Statement stmt2 = null;
    // @formatter:off
    final String dropUserTable = "DROP TABLE IF EXISTS User";
    final String createUserTable =
        "CREATE TABLE User ("
            + "UserId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "Username TEXT NOT NULL UNIQUE, "
            + "Password TEXT NOT NULL, "
            + "FirstName TEXT NOT NULL, "
            + "LastName TEXT NOT NULL, "
            + "Email TEXT NOT NULL UNIQUE, "
            + "RecordCount INTEGER NOT NULL, "
            + "CurrentBatchId INTEGER NOT NULL)";
    // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropUserTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createUserTable);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(stmt1);
      Database.closeSafely(stmt2);
    }
  }

  public ArrayList<User> getAll() throws DatabaseException {
    final ArrayList<User> allUsers = new ArrayList<User>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from User";
      pstmt = db.getConnection().prepareStatement(selectsql);
      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        final User resultUser = new User();

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
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
   */
  public int create(User newUser) throws DatabaseException {
    //@formatter:off
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String insertsql = "INSERT INTO User ("
          + "Username, Password, FirstName, LastName, "
          + "Email, RecordCount, CurrentBatchId)"
          + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      //@formatter:on
      pstmt = db.getConnection().prepareStatement(insertsql);
      pstmt.setString(1, newUser.getUsername());
      pstmt.setString(2, newUser.getPassword());
      pstmt.setString(3, newUser.getFirst());
      pstmt.setString(4, newUser.getLast());
      pstmt.setString(5, newUser.getEmail());
      pstmt.setInt(6, newUser.getRecordCount());
      pstmt.setInt(7, newUser.getCurrBatch());

      if (pstmt.executeUpdate() == 1) {
        final Statement stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        final int userid = resultset.getInt(1);
        newUser.setUserId(userid);
      } else {
        throw new DatabaseException("Unable to insert user into database.");
      }
    } catch (final SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return newUser.getUserId();
  }

  /**
   * Gets the user with specified username and password
   *
   * @param username
   * @param password
   * @return true if valid, else false
   */
  public User read(String username, String password) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    final User returnUser = new User();
    try {
      final String selectsql = "SELECT * from User WHERE Username = ? AND Password = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);

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
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
   */
  public void update(User user) throws DatabaseException {
    PreparedStatement pstmt = null;//@formatter:off
    try {
      final String selectsql = "UPDATE User SET FirstName = ?, LastName = ?,"
          + "Email = ?, RecordCount = ?, CurrentBatchId = ?"
          + "WHERE Username = ? AND Password = ?";  //@formatter:on
      pstmt = db.getConnection().prepareStatement(selectsql);

      pstmt.setString(1, user.getFirst());
      pstmt.setString(2, user.getLast());
      pstmt.setString(3, user.getEmail());
      pstmt.setInt(4, user.getRecordCount());
      pstmt.setInt(5, user.getCurrBatch());

      pstmt.setString(6, user.getUsername());
      pstmt.setString(7, user.getPassword());

      pstmt.executeUpdate();
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
    }
  }

  /**
   * Deletes the specified user.
   *
   * @param user the user
   */
  public void delete(User user) throws DatabaseException {
    PreparedStatement pstmt = null;
    try {
      final String selectsql = "DELETE from User WHERE Username = ? AND Password = ?";

      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setString(1, user.getUsername());
      pstmt.setString(2, user.getPassword());

      pstmt.executeUpdate();
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
    }
  }
}

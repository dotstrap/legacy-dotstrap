
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.User;


public class UserDAO {
  
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  
  private Database      db;

  
  public UserDAO(Database db) {

    this.db = db;
  }

  
  public void initTable() throws DatabaseException {
    String dropTable = "DROP TABLE IF EXISTS User";// @formatter:off
    String createTable =
        "CREATE TABLE User ("
            + "UserId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "Username TEXT NOT NULL UNIQUE, "
            + "Password TEXT NOT NULL, "
            + "FirstName TEXT NOT NULL, "
            + "LastName TEXT NOT NULL, "
            + "Email TEXT NOT NULL UNIQUE, "
            + "RecordCount INTEGER NOT NULL, "
            + "CurrentBatchId INTEGER NOT NULL)"; // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public ArrayList<User> getAll() throws DatabaseException {
    String query = "SELECT * from User";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<User> allUsers = new ArrayList<User>();
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
        return allUsers;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public int create(User newUser) throws DatabaseException {
    String query =
        "INSERT INTO User (Username, Password, FirstName, LastName, "
            + "Email, RecordCount, CurrentBatchId) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, newUser.getUsername());
      pstmt.setString(2, newUser.getPassword());
      pstmt.setString(3, newUser.getFirst());
      pstmt.setString(4, newUser.getLast());
      pstmt.setString(5, newUser.getEmail());
      pstmt.setInt(6, newUser.getRecordCount());
      pstmt.setInt(7, newUser.getCurrBatch());

      if (pstmt.executeUpdate() == 1) {
        try (Statement stmt = db.getConnection().createStatement();
            ResultSet resultset = stmt.executeQuery("SELECT last_insert_rowid()")) {
          resultset.next();
          int userid = resultset.getInt(1);
          newUser.setUserId(userid);
        }
      } else {
        throw new DatabaseException("Unable to insert username " + newUser.getUsername());
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
    return newUser.getUserId();
  }

  
  public User read(String username, String password) throws DatabaseException {
    String query = "SELECT * from User WHERE Username = ? AND Password = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, username);
      pstmt.setString(2, password);

      try (ResultSet resultset = pstmt.executeQuery()) {
        User returnUser = new User();

        if (!resultset.next()) {
          return null;
        }

        returnUser.setUserId(resultset.getInt(1));
        returnUser.setUsername(resultset.getString(2));
        returnUser.setPassword(resultset.getString(3));
        returnUser.setFirst(resultset.getString(4));
        returnUser.setLast(resultset.getString(5));
        returnUser.setEmail(resultset.getString(6));
        returnUser.setRecordCount(resultset.getInt(7));
        returnUser.setCurrBatch(resultset.getInt(8));

        if (resultset.next()) {
          throw new DatabaseException("Read more than one username: " + username
              + " from database...");
        }

        return returnUser;
      }
    } catch (SQLException e) {
      throw new DatabaseException("reading username: " + username + " from database...", e);
    }
  }

  
  public void update(User user) throws DatabaseException {
    String query =
        "UPDATE User SET FirstName = ?, LastName = ?, Email = ?, RecordCount = ?, CurrentBatchId = ?"
            + "WHERE Username = ? AND Password = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
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
    }
  }

  
  public void updateCurrentBatchId(int userId, int batchId) throws DatabaseException {
    String query = "UPDATE User SET CurrentBatchId = ? WHERE UserId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, batchId);
      pstmt.setInt(2, userId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public void clearCurrentBatchId(int userId) throws DatabaseException {
    updateCurrentBatchId(userId, -1);
    return;
  }

  
  public void incrementIndexedRecords(int userId) throws DatabaseException {
    String query = "UPDATE User SET IndexedRecords = IndexedRecords + 1 WHERE UserId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, userId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public void delete(User user) throws DatabaseException {
    String query = "DELETE from User WHERE Username = ? AND Password = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, user.getUsername());
      pstmt.setString(2, user.getPassword());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }
}

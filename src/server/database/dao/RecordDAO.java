/**
 * RecordDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Record;

/**
 * The Class RecordDAO.
 */
public class RecordDAO {

  /** The db. */
  private Database      db;

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  /**
   * Instantiates a new record DAO.
   *
   * @param db the db
   */
  public RecordDAO(Database db) {
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

    String dropRecordTable = "DROP TABLE IF EXISTS Record";// @formatter:off
    String createRecordTable =
        "CREATE TABLE Record ("
            + "RecordId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "FieldId INTEGER NOT NULL, "
            + "BatchId INTEGER NOT NULL, "
            + "BatchURL TEXT NOT NULL, "
            + "Data TEXT NOT NULL COllATE NOCASE, "
            + "RowNumber INTEGER NOT NULL, "
            + "ColumnNumber INTEGER NOT NULL)";     // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropRecordTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createRecordTable);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(stmt1);
      Database.closeSafely(stmt2);
    }
  }

  /**
   * Returns all Records in an array.
   *
   * @return -> record array if found, else return null
   * @throws DatabaseException
   */
  public ArrayList<Record> getAll() throws DatabaseException {
    ArrayList<Record> allRecords = new ArrayList<Record>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query = "SELECT * from Record";
      pstmt = db.getConnection().prepareStatement(query);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        Record resultRecord = new Record();

        resultRecord.setRecordId(resultset.getInt(1));
        resultRecord.setFieldId(resultset.getInt(2));
        resultRecord.setBatchId(resultset.getInt(3));
        resultRecord.setBatchURL(resultset.getString(4));
        resultRecord.setData(resultset.getString(5));
        resultRecord.setRowNum(resultset.getInt(6));
        resultRecord.setColNum(resultset.getInt(7));

        allRecords.add(resultRecord);
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allRecords;
  }

  /**
   * Returns all Records in an array.
   *
   * @param batchId the BatchId of the image whose records are requested
   * @return record array if found, else return null
   * @throws DatabaseException the database exception
   */
  public ArrayList<Record> getAll(int batchId) throws DatabaseException {
    ArrayList<Record> allRecords = new ArrayList<Record>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query = "SELECT * from Record where ImageId = ?";
      pstmt = db.getConnection().prepareStatement(query);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        Record resultRecord = new Record();

        resultRecord.setRecordId(resultset.getInt(1));
        resultRecord.setFieldId(resultset.getInt(2));
        resultRecord.setBatchId(batchId);
        resultRecord.setBatchURL(resultset.getString(4));
        resultRecord.setData(resultset.getString(5));
        resultRecord.setRowNum(resultset.getInt(6));
        resultRecord.setColNum(resultset.getInt(7));

        allRecords.add(resultRecord);
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allRecords;
  }

  /**
   * Searches the records table for a string.
   *
   * @param i the search field ids
   * @param s the search records
   * @return the records that contain the string
   * @throws DatabaseException the database exception
   */
  public List<Record> search(int fieldId, String dataValue) throws DatabaseException {
    ArrayList<Record> searchResult = new ArrayList<Record>();

    String query = "SELECT * from Record WHERE FieldId = ? AND Data = ?";;

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, fieldId);
      pstmt.setString(2, dataValue);

      try (ResultSet resultset = pstmt.executeQuery()) {

        while (resultset.next()) {
          Record resultRecord = new Record();

          resultRecord.setRecordId(resultset.getInt(1));
          resultRecord.setFieldId(resultset.getInt(2));
          resultRecord.setBatchId(resultset.getInt(3));
          resultRecord.setBatchURL(resultset.getString(4));
          resultRecord.setData(resultset.getString(5));
          resultRecord.setRowNum(resultset.getInt(6));
          resultRecord.setColNum(resultset.getInt(7));

          searchResult.add(resultRecord);
        }
      }
    } catch (SQLException e) {
      throw new DatabaseException("unable to search records...", e);
    }
    return searchResult;
  }

  /**
   * Inserts the given record to the database.
   *
   * @param newRecord the new record
   * @return the int
   * @throws DatabaseException the database exception
   */
  public int create(Record newRecord) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query =
          "INSERT into Record" + "(FieldId, BatchId, BatchURL, Data, RowNumber, ColumnNumber) "
              + "VALUES (?, ?, ?, ?, ?, ?)";
      pstmt = db.getConnection().prepareStatement(query);

      pstmt.setInt(1, newRecord.getFieldId());
      pstmt.setInt(2, newRecord.getBatchId());
      pstmt.setString(3, newRecord.getBatchURL());
      pstmt.setString(4, newRecord.getData());
      pstmt.setInt(5, newRecord.getRowNum());
      pstmt.setInt(6, newRecord.getColNum());

      if (pstmt.executeUpdate() == 1) {
        Statement stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        int id = resultset.getInt(1);
        newRecord.setRecordId(id);
      } else {
        throw new DatabaseException("unable to insert new record into database.");
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return newRecord.getRecordId();
  }

  /**
   * Gets the record.
   *
   * @param id the id
   * @return the record
   * @throws DatabaseException the database exception
   */
  public Record read(int id) throws DatabaseException {
    Record resultRecord = new Record();
    resultRecord.setRecordId(id);

    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query = "SELECT * from Record WHERE RecordId = ?";
      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, id);

      resultset = pstmt.executeQuery();

      resultset.next();

      resultRecord.setFieldId(resultset.getInt("FieldId"));
      resultRecord.setBatchId(resultset.getInt("BatchId"));
      resultRecord.setBatchURL(resultset.getString("BatchURL"));
      resultRecord.setData(resultset.getString("Data"));
      resultRecord.setRowNum(resultset.getInt("RowNumber"));
      resultRecord.setColNum(resultset.getInt("ColumnNumber"));

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return resultRecord;
  }

  /**
   * Deletes the specified record.
   *
   * @param record the record
   * @throws DatabaseException the database exception
   */
  public void delete(Record record) throws DatabaseException {
    PreparedStatement pstmt = null;
    try {
      String query = "DELETE from Record WHERE RecordId = ?";

      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, record.getRecordId());

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
    }
  }
}

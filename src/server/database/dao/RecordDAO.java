/**
 * RecordDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
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

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAO.
 */
public class RecordDAO {

  /** The db. */
  private final Database db;

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger(Database.LOG_NAME);
  }

  /**
   * Instantiates a new record DAO.
   *
   * @param db the db
   */
  public RecordDAO(Database db) {
    this.db = db;
  }

  public void initTable() throws DatabaseException {
    Statement stmt1 = null;
    Statement stmt2 = null;
    // @formatter:off
    final String dropRecordTable = "DROP TABLE IF EXISTS Record";
    final String createRecordTable =
        "CREATE TABLE Record ("
            + "RecordId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "FieldId INTEGER NOT NULL, "
            + "BatchId INTEGER NOT NULL, "
            + "BatchURL TEXT NOT NULL, "
            + "Data TEXT NOT NULL COllATE NOCASE, "
            + "RowNumber INTEGER NOT NULL, "
            + "ColumnNumber INTEGER NOT NULL)";
    // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropRecordTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createRecordTable);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
    final ArrayList<Record> allRecords = new ArrayList<Record>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from Record";
      pstmt = db.getConnection().prepareStatement(selectsql);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        final Record resultRecord = new Record();

        resultRecord.setRecordId(resultset.getInt(1));
        resultRecord.setFieldId(resultset.getInt(2));
        resultRecord.setBatchId(resultset.getInt(3));
        resultRecord.setBatchURL(resultset.getString(4));
        resultRecord.setData(resultset.getString(5));
        resultRecord.setRowNum(resultset.getInt(6));
        resultRecord.setColNum(resultset.getInt(7));

        allRecords.add(resultRecord);
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
   * @throws DatabaseException
   */
  public ArrayList<Record> getAll(int batchId) throws DatabaseException {
    final ArrayList<Record> allRecords = new ArrayList<Record>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from Record where ImageId = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        final Record resultRecord = new Record();

        resultRecord.setRecordId(resultset.getInt(1));
        resultRecord.setFieldId(resultset.getInt(2));
        resultRecord.setBatchId(batchId);
        resultRecord.setBatchURL(resultset.getString(4));
        resultRecord.setData(resultset.getString(5));
        resultRecord.setRowNum(resultset.getInt(6));
        resultRecord.setColNum(resultset.getInt(7));

        allRecords.add(resultRecord);
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allRecords;
  }

  /**
   * Searches the records table for a string
   *
   * @return the records that contain the string
   */
  public List<Record> search(List<Integer> searchFieldIds, List<String> searchRecords)
      throws DatabaseException {
    final ArrayList<Record> searchResult = new ArrayList<Record>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;

    final StringBuilder fieldString = new StringBuilder();
    fieldString.append("(");
    final StringBuilder recordString = new StringBuilder();
    recordString.append("(");

    for (int i = 0; i < searchFieldIds.size(); i++) {
      if (i > 0) {
        fieldString.append("OR ");
      }
      fieldString.append("FieldId = " + searchFieldIds.get(i) + " ");
    }

    for (int i = 0; i < searchRecords.size(); i++) {
      if (i > 0) {
        recordString.append("or ");
      }
      recordString.append("Record = '" + searchRecords.get(i) + "' ");
    }

    fieldString.append(") ");
    recordString.append(") ");

    try {
      final String selectsql =
          "SELECT * FROM Records " + "WHERE " + fieldString + "AND " + recordString
              + "COLLATE NOCASE";
      pstmt = db.getConnection().prepareStatement(selectsql);
      resultset = pstmt.executeQuery();

      while (resultset.next()) {
        final Record resultRecord = new Record();

        resultRecord.setRecordId(resultset.getInt(1));
        resultRecord.setFieldId(resultset.getInt(2));
        resultRecord.setBatchId(3);
        resultRecord.setBatchURL(resultset.getString(4));
        resultRecord.setData(resultset.getString(5));
        resultRecord.setRowNum(resultset.getInt(6));
        resultRecord.setColNum(resultset.getInt(7));

        searchResult.add(resultRecord);
      }
    } catch (final SQLException err) {
      throw new DatabaseException("Unable to get all records", err);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return searchResult;
  }

  /**
   * Inserts the given record to the database.
   *
   * @param record the record
   * @return the int
   */
  public int create(Record newRecord) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String insertsql =
          "insert into Record" + "(FieldId, BatchId, BatchURL, Data, RowNumber, ColumnNumber) "
              + "values (?, ?, ?, ?, ?, ?)";
      pstmt = db.getConnection().prepareStatement(insertsql);

      pstmt.setInt(1, newRecord.getFieldId());
      pstmt.setInt(2, newRecord.getBatchId());
      pstmt.setString(3, newRecord.getBatchURL());
      pstmt.setString(4, newRecord.getData());
      pstmt.setInt(5, newRecord.getRowNum());
      pstmt.setInt(6, newRecord.getColNum());

      if (pstmt.executeUpdate() == 1) {
        final Statement stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        final int id = resultset.getInt(1);
        newRecord.setRecordId(id);
      } else {
        throw new DatabaseException("Unable to insert new record into database.");
      }
    } catch (final SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
   */
  public Record read(int id) throws DatabaseException {
    final Record resultRecord = new Record();
    resultRecord.setRecordId(id);

    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from Record WHERE RecordId = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, id);

      resultset = pstmt.executeQuery();

      resultset.next();

      resultRecord.setFieldId(resultset.getInt("FieldId"));
      resultRecord.setBatchId(resultset.getInt("BatchId"));
      resultRecord.setBatchURL(resultset.getString("BatchURL"));
      resultRecord.setData(resultset.getString("Data"));
      resultRecord.setRowNum(resultset.getInt("RowNumber"));
      resultRecord.setColNum(resultset.getInt("ColumnNumber"));
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
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
   * @throws DatabaseException
   */
  public void delete(Record record) throws DatabaseException {
    PreparedStatement pstmt = null;
    try {
      final String selectsql = "DELETE from Record WHERE RecordId = ?";

      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, record.getRecordId());

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

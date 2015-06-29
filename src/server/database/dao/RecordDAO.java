package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Record;

public class RecordDAO {
  private Database db;

  public RecordDAO(Database db) {
    this.db = db;
  }

  public void initTable() throws DatabaseException {
    String dropTable = "DROP TABLE IF EXISTS Record";// @formatter:off
      String createTable =
          "CREATE TABLE Record ("
          + "RecordId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
          + "FieldId INTEGER NOT NULL, "
          + "BatchId INTEGER NOT NULL, "
          + "BatchURL TEXT NOT NULL, "
          + "Data TEXT NOT NULL COllATE NOCASE, "
          + "RowNumber INTEGER NOT NULL, "
          + "ColumnNumber INTEGER NOT NULL)";     // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public ArrayList<Record> getAll() throws DatabaseException {
    String query = "SELECT * from Record";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<Record> allRecordes = new ArrayList<Record>();
        while (resultset.next()) {
          Record resultRecord = new Record();

          resultRecord.setRecordId(resultset.getInt("RecordId"));
          resultRecord.setFieldId(resultset.getInt("FieldId"));
          resultRecord.setBatchId(resultset.getInt("BatchId"));
          resultRecord.setBatchURL(resultset.getString("BatchURL"));
          resultRecord.setData(resultset.getString("Data"));
          resultRecord.setRowNum(resultset.getInt("RowNumber"));
          resultRecord.setColNum(resultset.getInt("ColumnNumber"));

          allRecordes.add(resultRecord);
        }
        return allRecordes;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public ArrayList<Record> getAll(int batchId) throws DatabaseException {
    String query = "SELECT * FROM Record WHERE BatchId= ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, batchId);

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<Record> allRecordes = new ArrayList<Record>();
        while (resultset.next()) {
          Record resultRecord = new Record();

          resultRecord.setRecordId(resultset.getInt("RecordId"));
          resultRecord.setFieldId(resultset.getInt("FieldId"));
          resultRecord.setBatchId(resultset.getInt("BatchId"));
          resultRecord.setBatchURL(resultset.getString("BatchURL"));
          resultRecord.setData(resultset.getString("Data"));
          resultRecord.setRowNum(resultset.getInt("RowNumber"));
          resultRecord.setColNum(resultset.getInt("ColumnNumber"));

          allRecordes.add(resultRecord);
        }
        return allRecordes;
      }
    } catch (SQLException e) {
      throw new DatabaseException("occurred while getting all record from database", e);
    }
  }

  public List<Record> search(int fieldId, String dataValue) throws DatabaseException {
    String query = "SELECT * from Record WHERE FieldId = ? AND Data = ?";;

    ArrayList<Record> searchResult = new ArrayList<Record>();
    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, fieldId);
      pstmt.setString(2, dataValue);

      try (ResultSet resultset = pstmt.executeQuery()) {

        while (resultset.next()) {
          Record resultRecord = new Record();

          resultRecord.setRecordId(resultset.getInt("RecordId"));
          resultRecord.setFieldId(resultset.getInt("FieldId"));
          resultRecord.setBatchId(resultset.getInt("BatchId"));
          resultRecord.setBatchURL(resultset.getString("BatchURL"));
          resultRecord.setData(resultset.getString("Data"));
          resultRecord.setRowNum(resultset.getInt("RowNumber"));
          resultRecord.setColNum(resultset.getInt("ColumnNumber"));

          searchResult.add(resultRecord);
        }
      }
    } catch (SQLException e) {
      throw new DatabaseException("occurred while searching records...", e);
    }
    return searchResult;
  }

  public int create(Record newRecord) throws DatabaseException {
    String query =
        "INSERT into Record" + "(FieldId, BatchId, BatchURL, Data, RowNumber, ColumnNumber) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, newRecord.getFieldId());
      pstmt.setInt(2, newRecord.getBatchId());
      pstmt.setString(3, newRecord.getBatchURL());
      pstmt.setString(4, newRecord.getData());
      pstmt.setInt(5, newRecord.getRowNum());
      pstmt.setInt(6, newRecord.getColNum());

      if (pstmt.executeUpdate() == 1) {
        try (Statement stmt = db.getConnection().createStatement();
            ResultSet resultset = stmt.executeQuery("SELECT last_insert_rowid()")) {
          resultset.next();
          int id = resultset.getInt(1);
          newRecord.setRecordId(id);
          return id;
        }
      } else {
        throw new DatabaseException("ERROR occurred while inserting record: "
            + newRecord.toString());
      }
    } catch (SQLException e) {
      throw new DatabaseException("occurred while inserting record: " + newRecord.toString());
    }
  }

  public Record read(int id) throws DatabaseException {
    String query = "SELECT * from Record WHERE RecordId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, id);

      try (ResultSet resultset = pstmt.executeQuery()) {
        Record resultRecord = new Record();
        resultRecord.setRecordId(id);

        if (!resultset.next()) {
          return null;
        }

        resultRecord.setFieldId(resultset.getInt("FieldId"));
        resultRecord.setBatchId(resultset.getInt("BatchId"));
        resultRecord.setBatchURL(resultset.getString("BatchURL"));
        resultRecord.setData(resultset.getString("Data"));
        resultRecord.setRowNum(resultset.getInt("RowNumber"));
        resultRecord.setColNum(resultset.getInt("ColumnNumber"));

        return resultRecord;
      }

    } catch (SQLException e) {
      throw new DatabaseException("occurred while reading recordId: " + id, e);
    }
  }

  public List<Record> readByBatchId(int id) throws DatabaseException {
    String query = "SELECT * from Record WHERE BatchId = ?";

    ArrayList<Record> results = new ArrayList<Record>();
    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, id);

      try (ResultSet resultset = pstmt.executeQuery()) {
        while (resultset.next()) {
          Record resultRecord = new Record();
          resultRecord.setBatchId(id);

          if (!resultset.next()) {
            return null;
          }

          resultRecord.setFieldId(resultset.getInt("RecordId"));
          resultRecord.setFieldId(resultset.getInt("FieldId"));
          resultRecord.setBatchURL(resultset.getString("BatchURL"));
          resultRecord.setData(resultset.getString("Data"));
          resultRecord.setRowNum(resultset.getInt("RowNumber"));
          resultRecord.setColNum(resultset.getInt("ColumnNumber"));

          results.add(resultRecord);
        }
      }

    } catch (SQLException e) {
      throw new DatabaseException("occurred while reading batchId: " + id, e);
    }
    return results;
  }

  public void delete(Record record) throws DatabaseException {
    String query = "DELETE from Record WHERE RecordId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, record.getRecordId());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(
          "occurred deleting record with recordID: " + record.getRecordId(), e);
    }
  }
}

/**
 * BatchDAO.java
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

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAO.
 */
public class BatchDAO {

  /** The db. */
  private final Database db;

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger(Database.LOG_NAME);
  }

  /**
   * Instantiates a new batch DAO.
   *
   * @param db the db
   */
  public BatchDAO(Database db) {
    this.db = db;
  }

  public void initTable() throws DatabaseException {
    Statement stmt1 = null;
    Statement stmt2 = null;
    // @formatter:off
    final String dropBatchTable = "DROP TABLE IF EXISTS Batch";
    final String createBatchTable =
        "CREATE TABLE Batch ("
            + "BatchId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
            + "FilePath TEXT NOT NULL, "
            + "ProjectId INTEGER NOT NULL, "
            + "Status INTEGER NOT NULL, "
            + "CurrentUserId INTEGER NOT NULL)";
    // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropBatchTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createBatchTable);
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
   * Returns all batches in an array.
   *
   * @return -> batch array if found, else return null
   * @throws DatabaseException
   */
  public ArrayList<Batch> getAll() throws DatabaseException {

    final ArrayList<Batch> allBatches = new ArrayList<Batch>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from Batch";
      pstmt = db.getConnection().prepareStatement(selectsql);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        final Batch resultBatch = new Batch();

        resultBatch.setBatchId(resultset.getInt("BatchId"));
        resultBatch.setFilePath(resultset.getString("Filepath"));
        resultBatch.setProjectId(resultset.getInt("ProjectId"));
        resultBatch.setStatus(resultset.getInt("Status"));
        resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));

        allBatches.add(resultBatch);
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allBatches;
  }

  /**
   * Inserts the given batch into the database.
   *
   * @param batch the batch
   * @return the int
   * @throws DatabaseException
   */
  public int create(Batch batch) throws DatabaseException {

    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet resultset = null;
    try {
      final String insertsql =
          "INSERT INTO Batch (FilePath, ProjectId, Status, CurrentUserId) VALUES(?, ?, ?, ?)";
      pstmt = db.getConnection().prepareStatement(insertsql);

      pstmt.setString(1, batch.getFilePath());
      pstmt.setInt(2, batch.getProjectId());
      pstmt.setInt(3, batch.getStatus());
      pstmt.setInt(4, batch.getCurrUserId());

      if (pstmt.executeUpdate() == 1) {
        stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        final int batchid = resultset.getInt(1);
        batch.setBatchId(batchid);
      } else {
        throw new DatabaseException("Unable to insert new batch into database.");
      }
    } catch (final SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return batch.getBatchId();
  }

  /**
   * Gets the batch from the database.
   *
   * @param batchid the batchid
   * @return the batch
   * @throws DatabaseException the database exception
   */
  public Batch read(int batchid) throws DatabaseException {

    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    final Batch resultBatch = new Batch();
    try {
      final String selectsql = "SELECT * from Batch WHERE BatchId = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, batchid);

      resultset = pstmt.executeQuery();

      resultset.next();
      resultBatch.setBatchId(resultset.getInt(1));
      resultBatch.setFilePath(resultset.getString(2));
      resultBatch.setProjectId(resultset.getInt(3));
      resultBatch.setStatus(resultset.getInt(4));
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    if (resultBatch.getFilePath() == "") {
      return null;
    }
    return resultBatch;
  }

  /**
   * Gets a sample batch for a project
   *
   * @param projectId the id of the project the sample batch is in
   * @return the sample batch
   * */
  public Batch getIncompleteBatch(int projectId) throws DatabaseException {

    final Batch resultBatch = new Batch();
    resultBatch.setProjectId(projectId);
    PreparedStatement pstmt = null;
    ResultSet resultset = null;

    try {
      final String selectsql = "SELECT * from Batch WHERE ProjectId = ? AND Status = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);

      pstmt.setInt(1, projectId);
      pstmt.setInt(2, Batch.INCOMPLETE);

      resultset = pstmt.executeQuery();
      resultset.next();

      resultBatch.setBatchId(resultset.getInt("BatchId"));
      resultBatch.setFilePath(resultset.getString("FilePath"));
      resultBatch.setStatus(resultset.getInt("Status"));
      resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));
    } catch (final SQLException e) {
      throw new DatabaseException("Unable to get incomplete batch", e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return resultBatch;
  }

  /**
   * Gets a sample batch for a project
   *
   * @param projectId the id of the project the sample batch is in
   * @return the sample batch
   * */
  public Batch getSampleBatch(int projectId) throws DatabaseException {

    final Batch resultBatch = new Batch();
    resultBatch.setProjectId(projectId);
    PreparedStatement pstmt = null;
    ResultSet resultset = null;

    try {
      final String selectsql = "SELECT * from Batch WHERE ProjectId = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);

      pstmt.setInt(1, projectId);
      resultset = pstmt.executeQuery();

      resultset.next();
      resultBatch.setBatchId(resultset.getInt("BatchId"));
      resultBatch.setFilePath(resultset.getString("FilePath"));
      resultBatch.setStatus(resultset.getInt("Status"));
      resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));
    } catch (final SQLException e) {
      throw new DatabaseException("Unable to get sample batch", e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return resultBatch;
  }

  /**
   * updates the batch.
   *
   * @param batch -> batch to update with
   * @throws DatabaseException
   */

  public void update(Batch batch) throws DatabaseException {

    PreparedStatement pstmt = null;
    try {
      final String selectsql = "UPDATE Batch SET Status = ?" + "WHERE BatchId = ?";

      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, batch.getStatus());
      pstmt.setInt(2, batch.getBatchId());

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
   * Deletes the specified batch.
   *
   * @param batch the batch
   * @throws DatabaseException
   */
  public void delete(Batch batch) throws DatabaseException {

    PreparedStatement pstmt = null;
    try {
      final String selectsql = "DELETE from Batch WHERE BatchId = ?";

      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, batch.getBatchId());

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

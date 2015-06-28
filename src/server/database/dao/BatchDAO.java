
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Batch;


public class BatchDAO {

  
  private Database      db;

  
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  
  public BatchDAO(Database db) {
    this.db = db;
  }

  
  public void initTable() throws DatabaseException {
    String dropTable = "DROP TABLE IF EXISTS Batch";// @formatter:off
    String createTable =
        "CREATE TABLE Batch ("
            + "BatchId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "FilePath TEXT NOT NULL, "
            + "ProjectId INTEGER NOT NULL, "
            + "Status INTEGER NOT NULL, "
            + "CurrentUserId INTEGER NOT NULL)"; // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public ArrayList<Batch> getAll() throws DatabaseException {
    String query = "SELECT * from Batch";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<Batch> allBatches = new ArrayList<Batch>();
        while (resultset.next()) {
          Batch resultBatch = new Batch();

          resultBatch.setBatchId(resultset.getInt("BatchId"));
          resultBatch.setFilePath(resultset.getString("Filepath"));
          resultBatch.setProjectId(resultset.getInt("ProjectId"));
          resultBatch.setStatus(resultset.getInt("Status"));
          resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));

          allBatches.add(resultBatch);
        }
        return allBatches;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public int create(Batch newBatch) throws DatabaseException {
    String query =
        "INSERT INTO Batch (FilePath, ProjectId, Status, CurrentUserId) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, newBatch.getFilePath());
      pstmt.setInt(2, newBatch.getProjectId());
      pstmt.setInt(3, newBatch.getStatus());
      pstmt.setInt(4, newBatch.getCurrUserId());

      if (pstmt.executeUpdate() == 1) {

        try (Statement stmt = db.getConnection().createStatement();
            ResultSet resultset = stmt.executeQuery("SELECT last_insert_rowid()")) {
          resultset.next();
          int batchid = resultset.getInt(1);
          newBatch.setBatchId(batchid);
        }

      } else {
        throw new DatabaseException("Unable to insert batch into database....");
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
    return newBatch.getBatchId();
  }

  
  public Batch read(int batchId) throws DatabaseException {
    String query = "SELECT * FROM Batch WHERE BatchId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, batchId);

      try (ResultSet resultset = pstmt.executeQuery()) {
        Batch resultBatch = new Batch();

        if (!resultset.next()) {
          return null;
        }

        resultBatch.setBatchId(resultset.getInt("BatchId"));
        resultBatch.setFilePath(resultset.getString("FilePath"));
        resultBatch.setProjectId(resultset.getInt("ProjectId"));
        resultBatch.setStatus(resultset.getInt("Status"));
        resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));

        // if (resultset.next())
        // throw new DatabaseException("Read more than one batch: " + batchId
        // + " from database...");

        // if (resultBatch.getFilePath() == "") {
        // return null;
        // }

        return resultBatch;
      }
    } catch (SQLException e) {
      throw new DatabaseException("retrieving batch with BatchId " + batchId, e);
    }
  }

  
  public Batch getIncompleteBatch(int projectId) throws DatabaseException {
    String query = "SELECT * from Batch WHERE ProjectId = ? AND Status = ? AND CurrentUserId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, projectId);
      pstmt.setInt(2, Batch.INCOMPLETE);
      pstmt.setInt(3, -1); // FIXME: find a better way than using the default value

      try (ResultSet resultset = pstmt.executeQuery()) {
        Batch resultBatch = new Batch();
        resultBatch.setProjectId(projectId);

        if (!resultset.next()) {
          return null;
        }

        resultBatch.setBatchId(resultset.getInt("BatchId"));
        resultBatch.setFilePath(resultset.getString("FilePath"));
        resultBatch.setProjectId(resultset.getInt("ProjectId"));
        resultBatch.setStatus(resultset.getInt("Status"));
        // resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));

        // if (resultset.next())
        // throw new DatabaseException("Read more than one batch with projectId: " + projectId
        // + " from database...");

        // if (resultBatch.getFilePath() == "") {
        // return null;
        // }

        return resultBatch;
      }
    } catch (SQLException e) {
      throw new DatabaseException("Error retrieving batch from database by id", e);
    }
  }

  
  public Batch getSampleBatch(int projectId) throws DatabaseException {
    String query = "SELECT * from Batch WHERE ProjectId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, projectId);

      try (ResultSet resultset = pstmt.executeQuery()) {
        Batch resultBatch = new Batch();
        resultBatch.setProjectId(projectId);

        if (!resultset.next()) {
          return null;
        }

        resultBatch.setBatchId(resultset.getInt("BatchId"));
        resultBatch.setFilePath(resultset.getString("FilePath"));
        resultBatch.setProjectId(resultset.getInt("ProjectId"));
        resultBatch.setStatus(resultset.getInt("Status"));
        resultBatch.setCurrUserId(resultset.getInt("CurrentUserId"));

        //if (resultset.next()){
          //throw new DatabaseException("Read more than one  with projectId: " + projectId
              //+ " from database...");
        //}
        // if (resultBatch.getFilePath() == "") {
        // return null;
        // }

        return resultBatch;
      }
    } catch (SQLException e) {
      throw new DatabaseException("Unable to get sample batch", e);
    }
  }

  
  public void update(Batch batch) throws DatabaseException {
    String query = "UPDATE Batch SET Status = ? WHERE BatchId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, batch.getStatus());
      pstmt.setInt(2, batch.getBatchId());

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException("updating BatchID: " + batch.getBatchId(), e);
    }
  }

  
  public void assignBatchToUser(int batchId, int userId) throws DatabaseException {
    String query = "UPDATE Batch SET CurrentUserId = ? WHERE batchId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, userId);
      pstmt.setInt(2, batchId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException("assigning BatchID: " + batchId + " to UserId: " + userId, e);
    }
    return;
  }

  
  public void unassignBatch(int batchId) throws DatabaseException {
    assignBatchToUser(batchId, -1);
    return;
  }

  
  public void setStatus(int batchId, int status) throws DatabaseException {
    if (status != Batch.INCOMPLETE && status != Batch.ACTIVE && status != Batch.COMPLETE) {
      throw new DatabaseException("ERROR: Current status: " + status + " is not 0, 1, or 2...");
    }

    String query = "UPDATE Batch SET Status = ? WHERE BatchId = ?";
    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, status);
      pstmt.setInt(2, batchId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException("setting status to: " + status + " for BatchID: " + batchId, e);
    }

  }

  
  public void delete(Batch batch) throws DatabaseException {
    String query = "DELETE from Batch WHERE BatchId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, batch.getBatchId());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException("deleting batch with batchID: " + batch.getBatchId(), e);
    }
  }
}

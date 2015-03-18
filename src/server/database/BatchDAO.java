/**
 * BatchDAO.java
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

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAO.
 */
public class BatchDAO {

    /** The db. */
    private Database      db;

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    /**
     * Instantiates a new batch DAO.
     *
     * @param db
     *            the db
     */
    public BatchDAO(Database db) {
        this.db = db;
    }

    public void initTable() throws DatabaseException {
        logger.entering("server.database.BatchDAO", "initTable");

        Statement stmt1 = null;
        Statement stmt2 = null;

        String dropBatchTable = "DROP TABLE IF EXISTS Batch";
        String createBatchTable = "CREATE TABLE Batch ("
                + "BatchID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
                + "FilePath TEXT NOT NULL, "
                + "ProjectID INTEGER NOT NULL, "
                + "Status INTEGER NOT NULL, "
                + "CurrentUserID INTEGER NOT NULL)";

        try {
            stmt1 = db.getConnection().createStatement();
            stmt1.executeUpdate(dropBatchTable);

            stmt2 = db.getConnection().createStatement();
            stmt2.executeUpdate(createBatchTable);
        }  catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(stmt1);
            Database.closeSafely(stmt2);
        }

        logger.exiting("server.database.BatchDAO", "initTable");
    }

    /**
     * Returns all batches in an array.
     *
     * @return -> batch array if found, else return null
     * @throws DatabaseException
     */
    public ArrayList<Batch> getAll() throws DatabaseException {
        logger.entering("server.database.BatchDAO", "getAll");

        ArrayList<Batch> allBatches = new ArrayList<Batch>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Batch";
            pstmt = db.getConnection().prepareStatement(selectsql);

            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Batch resultBatch = new Batch();

                resultBatch.setBatchID(resultset.getInt("BatchID"));
                resultBatch.setFilePath(resultset.getString("Filepath"));
                resultBatch.setProjectID(resultset.getInt("ProjectID"));
                resultBatch.setStatus(resultset.getInt("Status"));

                allBatches.add(resultBatch);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.BatchDAO", "getAll");
        return allBatches;
    }

    /**
     * Inserts the given batch into the database.
     *
     * @param batch
     *            the batch
     * @return the int
     * @throws DatabaseException
     */
    public int create(Batch batch) throws DatabaseException {
        logger.entering("server.database.BatchDAO", "create");

        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO Batch (FilePath, ProjectID, Status, CurrentUserID)"
                    + "VALUES(?, ?, ?, ?)";
            pstmt = db.getConnection().prepareStatement(insertsql);

            pstmt.setString(1, batch.getFilePath());
            pstmt.setInt(2, batch.getProjectID());
            pstmt.setInt(3, batch.getStatus());
            pstmt.setInt(4, batch.getCurrUserID());

            if (pstmt.executeUpdate() == 1) {
                stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int batchid = resultset.getInt(1);
                batch.setBatchID(batchid);
            } else {
                throw new DatabaseException(
                        "Unable to insert new batch into database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.BatchDAO", "create");
        return batch.getBatchID();
    }

    /**
     * Gets the batch from the database.
     *
     * @param batchid
     *            the batchid
     * @return the batch
     * @throws DatabaseException
     *             the database exception
     */
    public Batch read(int batchid) throws DatabaseException {
        logger.entering("server.database.BatchDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Batch resultBatch = new Batch();
        try {
            String selectsql = "SELECT * from Batch WHERE BatchID = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, batchid);

            resultset = pstmt.executeQuery();

            resultset.next();
            resultBatch.setBatchID(resultset.getInt(1));
            resultBatch.setFilePath(resultset.getString(2));
            resultBatch.setProjectID(resultset.getInt(3));
            resultBatch.setStatus(resultset.getInt(4));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.BatchDAO", "read");
        if (resultBatch.getFilePath() == "") {
            return null;
        }
        return resultBatch;
    }

    /**
     * updates the batch.
     *
     * @param batch
     *            -> batch to update with
     * @throws DatabaseException
     */

    public void update(Batch batch) throws DatabaseException {
        logger.entering("server.database.BatchDAO", "update");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "UPDATE Batch SET Status = ?" + "WHERE BatchID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, batch.getStatus());
            pstmt.setInt(2, batch.getBatchID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.BatchDAO", "update");
    }

    /**
     * Deletes the specified batch.
     *
     * @param batch
     *            the batch
     * @throws DatabaseException
     */
    public void delete(Batch batch) throws DatabaseException {
        logger.entering("server.database.BatchDAO", "delete");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Batch WHERE BatchID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, batch.getBatchID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.BatchDAO", "delete");
    }
}

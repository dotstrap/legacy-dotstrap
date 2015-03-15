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
     * Instantiates a new batch dao.
     *
     * @param db
     *            the db
     */
    public BatchDAO(Database db) {
        this.db = db;
    }

    /**
     * returns all batches in an array.
     *
     * @return -> batch array if found, else return null
     * @throws DatabaseException
     */
    public ArrayList<Batch> getAll() throws DatabaseException {
        logger.entering("server.database.UserDAO", "getAll");

        ArrayList<Batch> allBatches = new ArrayList<Batch>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Batch";
            pstmt = db.getConnection().prepareStatement(selectsql);
            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Batch resultBatch = new Batch();

                resultBatch.setID(resultset.getInt("ID"));
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

        logger.exiting("server.database.UserDAO", "getAll");
        return allBatches;
    }

    /**
     * Adds the given batch to the database.
     *
     * @param batch
     *            the batch
     * @return the int
     * @throws DatabaseException
     */
    public int create(Batch batch) throws DatabaseException {
        logger.entering("server.database.UserDAO", "create");

        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO Batch (FilePath, ProjectID, Status)"
                    + "VALUES(?, ?, ?)";
            pstmt = db.getConnection().prepareStatement(insertsql);
            pstmt.setString(1, batch.getFilePath());
            pstmt.setInt(2, batch.getProjectID());
            pstmt.setInt(3, batch.getStatus());

            if (pstmt.executeUpdate() == 1) {
                stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int id = resultset.getInt(1);
                batch.setID(id);
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

        logger.exiting("server.database.UserDAO", "create");
        return batch.getID();
    }

    /**
     * Gets the batch from the database.
     *
     * @param id
     *            the id
     * @return the batch
     * @throws DatabaseException
     *             the database exception
     */
    public Batch read(int id) throws DatabaseException {
        logger.entering("server.database.UserDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Batch returnBatch = new Batch();
        try {
            String selectsql = "SELECT * from Batch WHERE ID = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, id);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnBatch.setID(resultset.getInt(1));
            returnBatch.setFilePath(resultset.getString(2));
            returnBatch.setProjectID(resultset.getInt(3));
            returnBatch.setStatus(resultset.getInt(4));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }
        if (returnBatch.getFilePath() == "") {
            return null;
        }

        logger.exiting("server.database.UserDAO", "read");
        return returnBatch;
    }

    /**
     * updates the batch.
     *
     * @param batch
     *            -> batch to update with
     * @throws DatabaseException
     */

    public void update(Batch batch) throws DatabaseException {
        logger.entering("server.database.UserDAO", "update");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "UPDATE Batch SET Status = ?" + "WHERE ID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, batch.getStatus());
            pstmt.setInt(2, batch.getID());

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
     * Deletes the specified batch.
     *
     * @param batch
     *            the batch
     * @throws DatabaseException
     */
    public void delete(Batch batch) throws DatabaseException {
        logger.entering("server.database.UserDAO", "delete");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Batch WHERE ID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, batch.getID());

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

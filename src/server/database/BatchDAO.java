/**
 * BatchDAO.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
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

        ArrayList<Batch> batches = new ArrayList<Batch>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Batch";
            pstmt = connection.prepareStatement(selectsql);

            resultset = pstmt.executeQuery();

            while (resultset.next()) {
                Batch returnBatch = new Batch();
                returnBatch.setID(resultset.getInt(1));
                returnBatch.setFilePath(resultset.getString(2));
                returnBatch.setProjectID(resultset.getInt(3));
                returnBatch.setState(resultset.getInt(4));
                batches.add(returnBatch);
            }
        } catch (Exception e) {
            String logMsg = " CAUSE: " + e.getCause() + "\n";
            logger.log(Level.SEVERE, e.toString() + logMsg);
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString() + logMsg);
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
            Database.closeSafely(resultset);
        }
        return batches;
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
        Connection connection = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO batch (FilePath, ProjectID, State)"
                    + "VALUES(?, ?, ?)";
            pstmt = connection.prepareStatement(insertsql);
            pstmt.setString(1, batch.getFilePath());
            pstmt.setInt(2, batch.getProjectID());
            pstmt.setInt(3, batch.getState());

            if (pstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                batch.setID(id);
            }
        } catch (Exception e) {
            String logMsg = " CAUSE: " + e.getCause() + "\n";
            logger.log(Level.SEVERE, e.toString() + logMsg);
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString() + logMsg);
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
            Database.closeSafely(stmt);
            Database.closeSafely(resultset);
        }
        return id;
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
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Batch returnBatch = new Batch();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Batch WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, id);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnBatch.setID(resultset.getInt(1));
            returnBatch.setFilePath(resultset.getString(2));
            returnBatch.setProjectID(resultset.getInt(3));
            returnBatch.setState(resultset.getInt(4));
        } catch (Exception e) {
            String logMsg = " CAUSE: " + e.getCause() + "\n";
            logger.log(Level.SEVERE, e.toString() + logMsg);
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString() + logMsg);
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
            Database.closeSafely(resultset);
        }
        if (returnBatch.getFilePath() == "") {
            return null;
        }
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
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "UPDATE Batch SET State = ?" + "WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, batch.getState());
            pstmt.setInt(2, batch.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            String logMsg = " CAUSE: " + e.getCause() + "\n";
            logger.log(Level.SEVERE, e.toString() + logMsg);
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString() + logMsg);
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
        }
    }

    /**
     * Deletes the specified batch.
     *
     * @param batch
     *            the batch
     * @throws DatabaseException
     */
    public void delete(Batch batch) throws DatabaseException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();

            String selectsql = "DELETE from Batch WHERE ID = ?";

            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, batch.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            String logMsg = " CAUSE: " + e.getCause() + "\n";
            logger.log(Level.SEVERE, e.toString() + logMsg);
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString() + logMsg);
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
        }
    }
}

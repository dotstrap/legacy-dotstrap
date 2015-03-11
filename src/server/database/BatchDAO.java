/**
 * BatchDAO.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
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

    /** The current database. */
    private Database      db;

    // Used for logging
    /** The logger. */
    private static Logger logger;
    
    /** The curr class. */
    private final String  currClass;

    /**
     * Instantiates a new batch dao.
     *
     * @param db the db
     */
    public BatchDAO(Database db) {
        logger = Logger.getLogger("server");
        currClass = Database.class.getName();
        this.db = db;
    }

    /**
     * Creates a new entry in the batch table.
     *
     * @param batch the batch table to insert into
     * @return the int
     */
    public int create(Batch batch) {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
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
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod + " from the "
                    + currClass + " class.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }       
        connection = null;
        pstmt = null;
        stmt = null;
        resultset = null;
        
        logger.exiting(currClass, currMethod);
        return id;
    }

    /**
     * Gets all the batches currently in the database
     * 
     * @return an ArraryList<Batch> of all batches
     */
    public ArrayList<Batch> getAll() {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
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
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod + " from the "
                    + currClass + " class.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        connection = null;
        pstmt = null;
        resultset = null;

        return batches;
    }

    /**
     * Gets the batch.
     *
     * @param id the id
     * @return the batch
     */
    public Batch read(int id) {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);

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
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod + " from the "
                    + currClass + " class.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        connection = null;
        pstmt = null;
        resultset = null;
        if (returnBatch.getFilePath() == "") {
            return null;
        }

        logger.exiting(currClass, currMethod);
        return returnBatch;
    }

    /**
     * Update.
     *
     * @param batch the batch
     */
    public void update(Batch batch) {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);

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
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod + " from the "
                    + currClass + " class.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        connection = null;
        pstmt = null;
        logger.exiting(currClass, currMethod);
    }

    /**
     * Delete.
     *
     * @param batch the batch
     */
    public void delete(Batch batch) {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);

        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Batch WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, batch.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod + " from the "
                    + currClass + " class.");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        
        connection = null;
        pstmt = null;
        logger.exiting(currClass, currMethod);
    }
}

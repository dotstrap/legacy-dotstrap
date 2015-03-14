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
     */
    public ArrayList<Batch> getAll() // REMOVED INT INDEX FROM ARGS
    {
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
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
            Database.closeSafely(resultset);
        }
        return batches;
    }

    /**
     * returns batch with matching ID.
     *
     * @param id
     *            -> ID of the batch to search for
     * @return -> batch if found, else return null
     */
    public Batch getBatch(int id) {
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
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
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
     */

    public void update(Batch batch) {
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
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
        }
    }

    /**
     * Adds the given batch to the database.
     *
     * @param batch
     *            the batch
     * @return the int
     */
    public int create(Batch batch) {
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
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
            Database.closeSafely(stmt);
            Database.closeSafely(resultset);
        }
        return id;
    }

    /**
     * Deletes the specified batch.
     *
     * @param batch the batch
     */
    public void delete(Batch batch) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();

            String selectsql = "DELETE from Batch WHERE ID = ?";

            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, batch.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(connection);
        }
    }
}

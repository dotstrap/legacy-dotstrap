package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchDAO.
 */
public class BatchDAO {

    /** The db. */
    private Database db;

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
     * returns batch with matching ID.
     *
     * @param id
     *            -> ID of the batch to search for
     * @return -> batch if found, else return null
     */
    public Batch getBatch(int id) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        Batch returnBatch = new Batch();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Batch WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, id);

            resultset = SQLstmt.executeQuery();

            resultset.next();
            returnBatch.setID(resultset.getInt(1));
            returnBatch.setFilePath(resultset.getString(2));
            returnBatch.setProjectID(resultset.getInt(3));
            returnBatch.setState(resultset.getInt(4));
        } catch (Exception e) {
            // e.printStackTrace();
        }

        connection = null;
        SQLstmt = null;
        resultset = null;
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
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "UPDATE Batch SET State = ?" + "WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, batch.getState());
            SQLstmt.setInt(2, batch.getID());

            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection = null;
        SQLstmt = null;
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
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Batch";
            SQLstmt = connection.prepareStatement(selectsql);

            resultset = SQLstmt.executeQuery();

            while (resultset.next()) {
                Batch returnBatch = new Batch();
                returnBatch.setID(resultset.getInt(1));
                returnBatch.setFilePath(resultset.getString(2));
                returnBatch.setProjectID(resultset.getInt(3));
                returnBatch.setState(resultset.getInt(4));
                batches.add(returnBatch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection = null;
        SQLstmt = null;
        resultset = null;

        return batches;
    }

    /**
     * Adds the.
     *
     * @param batch
     *            the batch
     * @return the int
     */
    public int add(Batch batch) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO batch (FilePath, ProjectID, State)"
                               + "VALUES(?, ?, ?)";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setString(1, batch.getFilePath());
            SQLstmt.setInt(2, batch.getProjectID());
            SQLstmt.setInt(3, batch.getState());

            if (SQLstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                batch.setID(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
        stmt = null;
        resultset = null;

        return id;
    }

    /**
     * Delete.
     *
     * @param batch
     *            the batch
     */
    public void delete(Batch batch) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Batch WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, batch.getID());

            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection = null;
        SQLstmt = null;
    }
}

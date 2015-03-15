package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAO.
 */
public class RecordDAO {

    /** The db. */
    private Database db;

    /**
     * Instantiates a new record dao.
     *
     * @param db
     *            the db
     */
    public RecordDAO(Database db) {
        this.db = db;
    }

    /**
     * Adds the.
     *
     * @param record
     *            the record
     * @return the int
     */
    public int add(Record record) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO Record (RowOnImage, BatchID, Data, FieldID)"
                               + "VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertsql);
            pstmt.setInt(1, record.getRecordNumber());
            pstmt.setInt(2, record.getBatchID());
            pstmt.setString(3, record.getData());
            pstmt.setInt(4, record.getFieldID());

            if (pstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                record.setID(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }

    /**
     * Search.
     *
     * @param id
     *            the id
     * @param value
     *            the value
     * @return the array list
     */
    public ArrayList<Record> search(int id, String value) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        ArrayList<Record> records = new ArrayList<Record>();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Record WHERE FieldID = ? AND Data = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, id);
            pstmt.setString(2, value);

            resultset = pstmt.executeQuery();

            while (resultset.next()) {
                Record returnRecord = new Record();
                returnRecord.setID(resultset.getInt(1));
                returnRecord.setRecordNumber(resultset.getInt(2));
                returnRecord.setBatchID(resultset.getInt(3));
                returnRecord.setData(resultset.getString(4));
                returnRecord.setFieldID(resultset.getInt(5));
                records.add(returnRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * Gets the record.
     *
     * @param id
     *            the id
     * @return the record
     */
    public Record getRecord(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Record returnRecord = new Record();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Record WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, id);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnRecord.setID(resultset.getInt(1));
            returnRecord.setRecordNumber(resultset.getInt(2));
            returnRecord.setBatchID(resultset.getInt(3));
            returnRecord.setData(resultset.getString(4));
            returnRecord.setFieldID(resultset.getInt(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
        resultset = null;
        return returnRecord;
    }

    /**
     * Gets the all.
     *
     * @return the all
     */
    public ArrayList<Record> getAll() {
        ArrayList<Record> records = new ArrayList<Record>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Record";
            pstmt = connection.prepareStatement(selectsql);

            resultset = pstmt.executeQuery();

            while (resultset.next()) {
                Record returnRecord = new Record();
                returnRecord.setID(resultset.getInt(1));
                returnRecord.setRecordNumber(resultset.getInt(2));
                returnRecord.setBatchID(resultset.getInt(3));
                returnRecord.setData(resultset.getString(4));
                returnRecord.setFieldID(resultset.getInt(5));
                records.add(returnRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
        resultset = null;

        return records;
    }

    /**
     * Delete.
     *
     * @param record
     *            the record
     */
    public void delete(Record record) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Record WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, record.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
    }
}

/**
 * RecordDAO.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
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
     * @param db the db
     */
    public RecordDAO(Database db) {
        this.db = db;
    }

    /**
     * Adds the.
     *
     * @param record the record
     * @return the int
     */
    public int add(Record record) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO Record (RowOnImage, BatchID, Data, FieldID)"
                    + "VALUES (?, ?, ?, ?)";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setInt(1, record.getRecordNumber());
            SQLstmt.setInt(2, record.getBatchID());
            SQLstmt.setString(3, record.getData());
            SQLstmt.setInt(4, record.getFieldID());

            if (SQLstmt.executeUpdate() == 1) {
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
        SQLstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }

    /**
     * Delete.
     *
     * @param record the record
     */
    public void delete(Record record) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectSQL = "DELETE from Record WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectSQL);
            SQLstmt.setInt(1, record.getID());

            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
    }

    public ArrayList<Record> getAll() {
        ArrayList<Record> records = new ArrayList<Record>();
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();
            String selectSQL = "SELECT * from Record";
            SQLstmt = connection.prepareStatement(selectSQL);

            resultset = SQLstmt.executeQuery();

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
        SQLstmt    = null;
        resultset  = null;

        return records;
    }

    /**
     * Gets the record.
     *
     * @param id the id
     * @return the record
     */
    public Record getRecord(int id) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        Record returnRecord = new Record();
        try {
            connection = db.getConnection();
            String selectSQL = "SELECT * from Record WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectSQL);
            SQLstmt.setInt(1, id);

            resultset = SQLstmt.executeQuery();

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
        SQLstmt    = null;
        resultset  = null;
        return returnRecord;
    }

    /**
     * Search.
     *
     * @param id the id
     * @param value the value
     * @return the array list
     */
    public ArrayList<Record> search(int id, String value) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        ArrayList<Record> records = new ArrayList<Record>();
        try {
            connection = db.getConnection();
            String selectSQL = "SELECT * from Record WHERE FieldID = ? AND Data = ?";
            SQLstmt = connection.prepareStatement(selectSQL);
            SQLstmt.setInt(1, id);
            SQLstmt.setString(2, value);

            resultset = SQLstmt.executeQuery();

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
}

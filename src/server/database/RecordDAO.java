package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAO.
 */
public class RecordDAO {

    /** The db. */
    private Database      db;

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    /**
     * Instantiates a new record DAO.
     *
     * @param db
     *            the db
     */
    public RecordDAO(Database db) {
        this.db = db;
    }

    /**
     * Returns all Records in an array.
     *
     * @return -> record array if found, else return null
     * @throws DatabaseException
     */
    public ArrayList<Record> getAll() throws DatabaseException {
        logger.entering("server.database.RecordDAO", "getAll");

        ArrayList<Record> allRecords = new ArrayList<Record>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Record";
            pstmt = db.getConnection().prepareStatement(selectsql);

            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Record resultRecord = new Record();

                resultRecord.setRecordID(resultset.getInt(1));
                resultRecord.setFieldID(resultset.getInt(2));
                resultRecord.setBatchID(resultset.getInt(3));
                resultRecord.setBatchURL(resultset.getString(4));
                resultRecord.setData(resultset.getString(5));
                resultRecord.setRowNum(resultset.getInt(6));
                resultRecord.setColNum(resultset.getInt(7));

                allRecords.add(resultRecord);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.RecordDAO", "getAll");
        return allRecords;
    }

    /**
     * Returns all Records in an array.
     *
     * @param batchID
     *            the BatchID of the image whose records are requested
     * @return record array if found, else return null
     * @throws DatabaseException
     */
    public ArrayList<Record> getAll(int batchID) throws DatabaseException {
        logger.entering("server.database.RecordDAO", "getAll");

        ArrayList<Record> allRecords = new ArrayList<Record>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Record where ImageID = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);

            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Record resultRecord = new Record();

                resultRecord.setRecordID(resultset.getInt(1));
                resultRecord.setFieldID(resultset.getInt(2));
                resultRecord.setBatchID(batchID);
                resultRecord.setBatchURL(resultset.getString(4));
                resultRecord.setData(resultset.getString(5));
                resultRecord.setRowNum(resultset.getInt(6));
                resultRecord.setColNum(resultset.getInt(7));

                allRecords.add(resultRecord);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.RecordDAO", "getAll");
        return allRecords;
    }

    /**
     * Searches the records table for a string
     * @return          the records that contain the string
     * */
    public List<Record> search(List<Integer> searchFieldIDs, List<String> searchRecords) throws DatabaseException {
        logger.entering("server.database.RecordDAO", "search");

        ArrayList<Record> searchResult = new ArrayList<Record>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;

        StringBuilder fieldString = new StringBuilder();
        fieldString.append("(");
        StringBuilder recordString = new StringBuilder();
        recordString.append("(");

        for (int i = 0; i < searchFieldIDs.size(); i++) {
            if (i > 0)
                fieldString.append("OR ");
            fieldString.append("FieldID = " + searchFieldIDs.get(i) + " ");
        }

        for (int i = 0; i < searchRecords.size(); i++) {
            if (i > 0)
                recordString.append("or ");
            recordString.append("Record = '" + searchRecords.get(i) + "' ");
        }

        fieldString.append(") ");
        recordString.append(") ");

        try {
            String selectsql = "SELECT * FROM Records "
                    + "WHERE " + fieldString + "AND " + recordString + "COLLATE NOCASE";
            pstmt  = db.getConnection().prepareStatement(selectsql);
            resultset = pstmt.executeQuery();

            while (resultset.next()) {
                Record resultRecord = new Record();

                resultRecord.setRecordID(resultset.getInt(1));
                resultRecord.setFieldID(resultset.getInt(2));
                resultRecord.setBatchID(3);
                resultRecord.setBatchURL(resultset.getString(4));
                resultRecord.setData(resultset.getString(5));
                resultRecord.setRowNum(resultset.getInt(6));
                resultRecord.setColNum(resultset.getInt(7));

                searchResult.add(resultRecord);
            }
        }
        catch (SQLException err) {
            throw new DatabaseException("Unable to get all records", err);
        }
        finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.RecordDAO", "search");
        return searchResult;
    }

    /**
     * Inserts the given record to the database.
     *
     * @param record
     *            the record
     * @return the int
     */
    public int create(Record newRecord) throws DatabaseException {
        logger.entering("server.database.RecordDAO", "create");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "insert into Record"
                    + "(FieldID, BatchID, BatchURL, Data, RowNumber, ColumnNumber) "
                    + "values (?, ?, ?, ?, ?, ?)";
            pstmt = db.getConnection().prepareStatement(insertsql);

            pstmt.setInt(1, newRecord.getFieldID());
            pstmt.setInt(2, newRecord.getBatchID());
            pstmt.setString(3, newRecord.getBatchURL());
            pstmt.setString(4, newRecord.getData());
            pstmt.setInt(5, newRecord.getRowNum());
            pstmt.setInt(6, newRecord.getColNum());

            if (pstmt.executeUpdate() == 1) {
                Statement stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int id = resultset.getInt(1);
                newRecord.setRecordID(id);
            } else {
                throw new DatabaseException(
                        "Unable to insert new record into database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.RecordDAO", "create");
        return newRecord.getRecordID();
    }

    /**
     * Gets the record.
     *
     * @param id
     *            the id
     * @return the record
     */
    public Record read(int id) throws DatabaseException {
        logger.entering("server.database.RecordDAO", "read");

        Record resultRecord = new Record();
        resultRecord.setRecordID(id);

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Record WHERE RecordID = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, id);

            resultset = pstmt.executeQuery();

            resultset.next();

            resultRecord.setFieldID(resultset.getInt("FieldID"));
            resultRecord.setBatchID(resultset.getInt("BatchID"));
            resultRecord.setBatchURL(resultset.getString("BatchURL"));
            resultRecord.setData(resultset.getString("Data"));
            resultRecord.setRowNum(resultset.getInt("RowNumber"));
            resultRecord.setColNum(resultset.getInt("ColumnNumber"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.RecordDAO", "read");
        return resultRecord;
    }

    /**
     * Deletes the specified record.
     *
     * @param record
     *            the record
     * @throws DatabaseException
     */
    public void delete(Record record) throws DatabaseException {
        logger.entering("server.database.RecordDAO", "delete");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Record WHERE RecordID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, record.getRecordID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.ProjectDAO", "delete");
    }
}

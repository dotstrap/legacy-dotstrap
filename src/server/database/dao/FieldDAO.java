/**
 * FieldDAO.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Field;

/**
 * The Class FieldDAO. Interfaces with the database to CRUD fields & getAll()
 * fields.
 */
public class FieldDAO {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger(Database.LOG_NAME);
    }

    /** The db. */
    private Database db;

    /**
     * Instantiates a new field dao.
     *
     * @param db
     *            the db
     */
    public FieldDAO(Database db) {
        this.db = db;
    }

    public void initTable() throws DatabaseException {
        logger.entering("server.database.FieldDAO", "initTable");

        Statement stmt1 = null;
        Statement stmt2 = null;

        String dropFieldTable = "DROP TABLE IF EXISTS Field";
        String createFieldTable = "CREATE TABLE Field ("
                + "FieldId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "ProjectId INTEGER NOT NULL, " + "Title TEXT NOT NULL, "
                + "KnownData TEXT NOT NULL, " + "HelpURL TEXT NOT NULL, "
                + "XCoordinate INTEGER NOT NULL, " + "Width INTEGER NOT NULL, "
                + "ColumnNumber INTEGER NOT NULL)";
        try {
            stmt1 = db.getConnection().createStatement();
            stmt1.executeUpdate(dropFieldTable);

            stmt2 = db.getConnection().createStatement();
            stmt2.executeUpdate(createFieldTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(stmt1);
            Database.closeSafely(stmt2);
        }

        logger.exiting("server.database.FieldDAO", "initTable");
    }

    public ArrayList<Field> getAll() throws DatabaseException {
        logger.entering("server.database.FieldDAO", "getAll");

        ArrayList<Field> allFields = new ArrayList<Field>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Field";
            pstmt = db.getConnection().prepareStatement(selectsql);
            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Field resultField = new Field();

                resultField.setFieldId(resultset.getInt("FieldId"));
                resultField.setProjectId(resultset.getInt("ProjectId"));
                resultField.setTitle(resultset.getString("Title"));

                resultField.setKnownData(resultset.getString("KnownData"));
                resultField.setHelpURL(resultset.getString("HelpURL"));

                resultField.setxCoord(resultset.getInt("XCoordinate"));
                resultField.setWidth(resultset.getInt("Width"));
                resultField.setWidth(resultset.getInt("ColumnNumber"));

                allFields.add(resultField);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.FieldDAO", "getAll");
        return allFields;
    }

    public ArrayList<Field> getAll(int ProjectId) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "getAll");

        ArrayList<Field> allFields = new ArrayList<Field>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Field WHERE ProjectId = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);
            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Field resultField = new Field();

                resultField.setFieldId(resultset.getInt("FieldId"));
                resultField.setProjectId(resultset.getInt("ProjectId"));
                resultField.setTitle(resultset.getString("Title"));

                resultField.setKnownData(resultset.getString("KnownData"));
                resultField.setHelpURL(resultset.getString("HelpURL"));

                resultField.setxCoord(resultset.getInt("XCoordinate"));
                resultField.setWidth(resultset.getInt("Width"));
                resultField.setWidth(resultset.getInt("ColumnNumber"));

                allFields.add(resultField);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.FieldDAO", "getAll");
        return allFields;
    }

    /**
     * Gets the id for the field of a project at a certain column
     *
     * @param projectId
     *            the projectId for the desired fieldId
     * @param colNum
     *            the colNum for the desired fieldId
     * @return the fieldId
     * @throws DatabaseException
     */
    public int getFieldId(int projectId, int colNum) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "getFieldId");

        int fieldId = -1;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT FieldId FROM Field "
                    + "WHERE ProjectId = ? AND ColumnNumber = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setInt(1, projectId);
            pstmt.setInt(2, colNum);
            resultset = pstmt.executeQuery();

            resultset.next();
            fieldId = resultset.getInt("FieldId");
            assert (fieldId > 0);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.FieldDAO", "getFieldId");
        return fieldId;
    }

    /**
     * Creates a new field.
     *
     * @param newField
     *            the field
     * @return the int FieldId of the field
     */
    public int create(Field newField) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "create");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO Field ("
                    + "ProjectId, Title, KnownData, HelpURL, XCoordinate, Width, ColumnNumber)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = db.getConnection().prepareStatement(insertsql);
            pstmt.setInt(1, newField.getProjectId());
            pstmt.setString(2, newField.getTitle());
            pstmt.setString(3, newField.getKnownData());
            pstmt.setString(4, newField.getHelpURL());
            pstmt.setInt(5, newField.getxCoord());
            pstmt.setInt(6, newField.getWidth());
            pstmt.setInt(7, newField.getColNum());

            if (pstmt.executeUpdate() == 1) {
                Statement stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int fieldId = resultset.getInt(1);
                newField.setFieldId(fieldId);
            } else {
                throw new DatabaseException(
                        "Unable to insert field into database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.FieldDAO", "create");
        return newField.getFieldId();
    }

    /**
     * Gets the field with specified projectId and title
     *
     * @param projectid
     * @param title
     * @return true if valid, else false
     */
    public Field read(int projectId, String title) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Field returnField = new Field();
        try {
            String selectsql = "SELECT * from Field WHERE ProjectId = ? AND Title = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setInt(1, projectId);
            pstmt.setString(2, title);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnField.setFieldId(resultset.getInt(1));
            returnField.setProjectId(projectId);
            returnField.setTitle(title);
            returnField.setKnownData(resultset.getString(4));
            returnField.setHelpURL(resultset.getString(5));
            returnField.setxCoord(resultset.getInt(6));
            returnField.setWidth(resultset.getInt(7));
            returnField.setColNum(resultset.getInt(8));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
            // return null;
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.FieldDAO", "read");
        return returnField;
    }

    /**
     * Updates the given field with the provided information.
     *
     * @param field
     *            the field
     */
    public void update(Field field) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "update");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "UPDATE Field SET KnownData = ?, HelpURL = ?,"
                    + "XCoordinate = ?, Width = ?"
                    + "WHERE ProjectId = ? AND Title = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setString(1, field.getKnownData());
            pstmt.setString(2, field.getHelpURL());
            pstmt.setInt(3, field.getxCoord());
            pstmt.setInt(4, field.getWidth());

            pstmt.setString(5, field.getTitle());
            pstmt.setInt(6, field.getColNum());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.FieldDAO", "update");
    }

    /**
     * Deletes the specified field.
     *
     * @param field
     *            the field
     */
    public void delete(Field field) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "delete");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Field WHERE ProjectId = ? AND Title = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, field.getProjectId());
            pstmt.setString(2, field.getTitle());

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
        }

        logger.exiting("server.database.FieldDAO", "delete");
    }
}

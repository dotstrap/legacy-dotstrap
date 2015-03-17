/**
 * FieldDAO.java
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

import shared.model.Field;

/**
 * The Class FieldDAO. Interfaces with the database to CRUD fields & getAll()
 * fields.
 */
public class FieldDAO {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
   }

    /** The db. */
    private Database      db;

    /**
     * Instantiates a new field dao.
     *
     * @param db
     *            the db
     */
    public FieldDAO(Database db) {
        this.db = db;
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

                resultField.setFieldID(resultset.getInt("FieldID"));
                resultField.setProjectID(resultset.getInt("ProjectID"));
                resultField.setTitle(resultset.getString("Title"));

                resultField.setKnownData(resultset.getString("KnownData"));
                resultField.setHelpURL(resultset.getString("HelpURL"));
                resultField.setFieldPath(resultset.getString("FieldPath"));

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
     * Creates a new field.
     *
     * @param newField
     *            the field
     * @return the int FieldID of the field
     */
    public int create(Field newField) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "create");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO Field (ProjectID, Title, KnownData, HelpURL, FieldPath, XCoordinate, Width)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = db.getConnection().prepareStatement(insertsql);
            pstmt.setInt(1, newField.getProjectID());
            pstmt.setString(2, newField.getTitle());
            pstmt.setString(3, newField.getKnownData());
            pstmt.setString(4, newField.getHelpURL());
            pstmt.setString(5, newField.getFieldPath());
            pstmt.setInt(6, newField.getxCoord());
            pstmt.setInt(7, newField.getWidth());
            pstmt.setInt(8, newField.getColNum());

            if (pstmt.executeUpdate() == 1) {
                Statement stmt = db.getConnection().createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                int fieldid = resultset.getInt(1);
                newField.setFieldID(fieldid);
            } else {
                throw new DatabaseException("Unable to insert field into database.");
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
        return newField.getFieldID();
    }

    /**
     * Gets the field with specified projectid and title
     *
     * @param projectid
     * @param title
     * @return true if valid, else false
     */
    public Field read(int projectID, String title) throws DatabaseException {
        logger.entering("server.database.FieldDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Field returnField = new Field();
        try {
            String selectsql = "SELECT * from Field WHERE ProjectID = ? AND Title = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);

            pstmt.setInt(1, projectID);
            pstmt.setString(2, title);

            resultset = pstmt.executeQuery();

            resultset.next();
            returnField.setFieldID(resultset.getInt(1));
            returnField.setProjectID(projectID);
            returnField.setTitle(title);
            returnField.setKnownData(resultset.getString(4));
            returnField.setHelpURL(resultset.getString(5));
            returnField.setFieldPath(resultset.getString(6));
            returnField.setxCoord(resultset.getInt(7));
            returnField.setWidth(resultset.getInt(8));
            returnField.setColNum(resultset.getInt(9));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
            //return null;
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
                    + "FieldPath = ?, XCoordinate = ?, Width = ?"
                    + "WHERE ProjectID = ? AND Title = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);

            //TODO: IDK if this is the correct order...
            pstmt.setString(1, field.getKnownData());
            pstmt.setString(2, field.getHelpURL());
            pstmt.setString(3, field.getFieldPath());
            pstmt.setInt(4, field.getxCoord());
            pstmt.setInt(5, field.getWidth());

            pstmt.setString(6, field.getFieldPath());
            pstmt.setString(7, field.getTitle());
            pstmt.setInt(8, field.getColNum());

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

        // Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Field WHERE ProjectID = ? AND Title = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, field.getProjectID());
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

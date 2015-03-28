/**
 * FieldDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Field;

/**
 * The Class FieldDAO. Interfaces with the database to CRUD fields & getAll() fields.
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
   * @param db the db
   */
  public FieldDAO(Database db) {
    this.db = db;
  }

  /**
   * Initializes the table.
   *
   * @throws DatabaseException the database exception
   */
  public void initTable() throws DatabaseException {
    String dropTable = "DROP TABLE IF EXISTS Field";
    String createTable =// @formatter:off
        "CREATE TABLE Field ("
            + "FieldId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "ProjectId INTEGER NOT NULL, "
            + "Title TEXT NOT NULL, "
            + "KnownData TEXT NOT NULL, "
            + "HelpURL TEXT NOT NULL, "
            + "XCoordinate INTEGER NOT NULL, "
            + "Width INTEGER NOT NULL, "
            + "ColumnNumber INTEGER NOT NULL)";     // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public ArrayList<Field> getAll() throws DatabaseException {
    String query = "SELECT * from Field";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<Field> allFields = new ArrayList<Field>();
        while (resultset.next()) {
          Field resultField = new Field();

          resultField.setFieldId(resultset.getInt("FieldId"));
          resultField.setProjectId(resultset.getInt("ProjectId"));
          resultField.setTitle(resultset.getString("Title"));

          resultField.setKnownData(resultset.getString("KnownData"));
          resultField.setHelpURL(resultset.getString("HelpURL"));

          resultField.setXCoord(resultset.getInt("XCoordinate"));
          resultField.setWidth(resultset.getInt("Width"));
          resultField.setWidth(resultset.getInt("ColumnNumber"));

          allFields.add(resultField);
        }
        return allFields;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  public List<Field> getAll(int projectId) throws DatabaseException {
    String query = "SELECT * from Field WHERE ProjectId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

        pstmt.setInt(1, projectId);
      try (ResultSet resultset = pstmt.executeQuery()) {

        List<Field> allFields = new ArrayList<Field>();
        while (resultset.next()) {
          Field resultField = new Field();
System.out.println("curr project: " + projectId);
          resultField.setFieldId(resultset.getInt("FieldId"));
           resultField.setProjectId(projectId);
          //resultField.setProjectId(resultset.getInt("ProjectId"));
          resultField.setTitle(resultset.getString("Title"));

          resultField.setKnownData(resultset.getString("KnownData"));
          resultField.setHelpURL(resultset.getString("HelpURL"));

          resultField.setXCoord(resultset.getInt("XCoordinate"));
          resultField.setWidth(resultset.getInt("Width"));
          resultField.setWidth(resultset.getInt("ColumnNumber"));

          allFields.add(resultField);
        }
        return allFields;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  /**
   * Gets the id for the field of a project at a certain column.
   *
   * @param projectId the projectId for the desired fieldId
   * @param colNum the colNum for the desired fieldId
   * @return the fieldId
   * @throws DatabaseException the database exception
   */
  public int getFieldId(int projectId, int colNum) throws DatabaseException {

    int fieldId = -1;
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      String query = "SELECT FieldId FROM Field WHERE ProjectId = ? AND ColumnNumber = ?";
      pstmt = db.getConnection().prepareStatement(query);

      pstmt.setInt(1, projectId);
      pstmt.setInt(2, colNum);
      resultset = pstmt.executeQuery();

      resultset.next();
      fieldId = resultset.getInt("FieldId");
      assert (fieldId > 0);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return fieldId;
  }

  /**
   * Creates a new field.
   *
   * @param newField the field
   * @return the int FieldId of the field
   * @throws DatabaseException the database exception
   */
  public int create(Field newField) throws DatabaseException {

    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      // @formatter:off
      String query =
          "INSERT INTO Field ("
              + "ProjectId, Title, KnownData, HelpURL, "
              + "XCoordinate, Width, ColumnNumber)"
              + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      // @formatter:on
      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, newField.getProjectId());
      pstmt.setString(2, newField.getTitle());
      pstmt.setString(3, newField.getKnownData());
      pstmt.setString(4, newField.getHelpURL());
      pstmt.setInt(5, newField.getXCoord());
      pstmt.setInt(6, newField.getWidth());
      pstmt.setInt(7, newField.getColNum());

      if (pstmt.executeUpdate() == 1) {
        Statement stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        int fieldId = resultset.getInt(1);
        newField.setFieldId(fieldId);
      } else {
        throw new DatabaseException("Unable to insert field into database.");
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return newField.getFieldId();
  }

  /**
   * Gets the field with specified projectId and title.
   *
   * @param projectId the project id
   * @param title the title
   * @return true if valid, else false
   * @throws DatabaseException the database exception
   */
  public Field read(int projectId, String title) throws DatabaseException {
    String query = "SELECT * from Field WHERE ProjectId = ? AND Title = ?";
    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, projectId);
      pstmt.setString(2, title);

      try (ResultSet resultset = pstmt.executeQuery()) {
        Field returnField = new Field();

        if (!resultset.next()) {
          return null;
        }

        returnField.setFieldId(resultset.getInt("FieldId"));
        // returnField.setProjectId(projectId);
        // returnField.setTitle(title);
        returnField.setKnownData(resultset.getString("KnownData"));
        returnField.setHelpURL(resultset.getString("HelpURL"));
        returnField.setXCoord(resultset.getInt("XCoordinate"));
        returnField.setWidth(resultset.getInt("Width"));
        returnField.setColNum(resultset.getInt("ColumnNumber"));

        // if (resultset.next())
        // throw new DatabaseException("Read more than one field with projectId: " + projectId
        // + " from database...");

        return returnField;
      }
    } catch (SQLException e) {
      throw new DatabaseException("retrieving field with ProjectId " + projectId, e);
    }
  }

  /**
   * Updates the given field with the provided information.
   *
   * @param field the field
   * @throws DatabaseException the database exception
   */
  public void update(Field field) throws DatabaseException {

    PreparedStatement pstmt = null;
    try {
      String query =
          "UPDATE Field SET KnownData = ?, HelpURL = ?, XCoordinate = ?, Width = ? "
              + "WHERE ProjectId = ? AND Title = ?";

      pstmt = db.getConnection().prepareStatement(query);

      pstmt.setString(1, field.getKnownData());
      pstmt.setString(2, field.getHelpURL());
      pstmt.setInt(3, field.getXCoord());
      pstmt.setInt(4, field.getWidth());

      pstmt.setString(5, field.getTitle());
      pstmt.setInt(6, field.getColNum());

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
    }
  }

  /**
   * Deletes the specified field.
   *
   * @param field the field
   * @throws DatabaseException the database exception
   */
  public void delete(Field field) throws DatabaseException {

    PreparedStatement pstmt = null;
    try {
      String query = "DELETE from Field WHERE ProjectId = ? AND Title = ?";

      pstmt = db.getConnection().prepareStatement(query);
      pstmt.setInt(1, field.getProjectId());
      pstmt.setString(2, field.getTitle());

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    } finally {
      Database.closeSafely(pstmt);
    }
  }
}

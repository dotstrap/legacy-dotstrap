/**
 * FieldDAO.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Field;

/**
 * The Class FieldDAO.
 */
public class FieldDAO {

  private static Logger logger;

  static {
    logger = Logger.getLogger("server");
  }

  private Database db;

  /**
   * Instantiates a new field dao.
   *
   * @param db the database
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
    String createTable = // @formatter:off
        "CREATE TABLE Field ("
            + "FieldId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "ProjectId INTEGER NOT NULL, "
            + "Title TEXT NOT NULL, "
            + "KnownData TEXT NOT NULL, "
            + "HelpURL TEXT NOT NULL, "
            + "XCoordinate INTEGER NOT NULL, "
            + "Width INTEGER, "
            + "ColumnNumber INTEGER)"; // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  /**
   * Creates the.
   *
   * @param newField the new field
   * @return the int
   * @throws DatabaseException the database exception
   */
  public int create(Field newField) throws DatabaseException {
    String query = "INSERT INTO Field ("
        + "ProjectId, Title, KnownData, HelpURL, "
        + "XCoordinate, Width, ColumnNumber)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      pstmt.setInt(1, newField.getProjectId());
      pstmt.setString(2, newField.getTitle());
      pstmt.setString(3, newField.getKnownData());
      pstmt.setString(4, newField.getHelpURL());
      pstmt.setInt(5, newField.getXCoord());
      pstmt.setInt(6, newField.getWidth());
      pstmt.setInt(7, newField.getColNum());

      if (pstmt.executeUpdate() == 1) {

        try (Statement stmt = db.getConnection().createStatement();
            ResultSet resultset =
                stmt.executeQuery("SELECT last_insert_rowid()")) {
          resultset.next();
          int fieldId = resultset.getInt(1);
          newField.setFieldId(fieldId);
        }

      } else {
        throw new DatabaseException("Unable to insert field into database.");
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
    return newField.getFieldId();
  }

  /**
   * Deletes the.
   *
   * @param field the field
   * @throws DatabaseException the database exception
   */
  public void delete(Field field) throws DatabaseException {
    String query = "DELETE from Field WHERE ProjectId = ? AND Title = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, field.getProjectId());
      pstmt.setString(2, field.getTitle());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(
          "occurred deleting field: " + field.getTitle(), e);
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
          resultField.setColNum(resultset.getInt("ColumnNumber"));

          allFields.add(resultField);
        }
        return allFields;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  /**
   * Gets the all.
   *
   * @param projectId the project id
   * @return the all
   * @throws DatabaseException the database exception
   */
  public List<Field> getAll(int projectId) throws DatabaseException {
    String query = "SELECT * from Field WHERE ProjectId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      pstmt.setInt(1, projectId);
      try (ResultSet resultset = pstmt.executeQuery()) {

        List<Field> allFields = new ArrayList<Field>();
        while (resultset.next()) {
          Field resultField = new Field();

          resultField.setFieldId(resultset.getInt("FieldId"));
          resultField.setProjectId(resultset.getInt("ProjectId"));
          resultField.setTitle(resultset.getString("Title"));
          resultField.setKnownData(resultset.getString("KnownData"));
          resultField.setHelpURL(resultset.getString("HelpURL"));
          resultField.setXCoord(resultset.getInt("XCoordinate"));
          resultField.setWidth(resultset.getInt("Width"));
          resultField.setColNum(resultset.getInt("ColumnNumber"));

          allFields.add(resultField);
        }
        return allFields;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  /**
   * Gets the field id.
   *
   * @param projectId the project id
   * @param colNum the col num
   * @return the field id
   * @throws DatabaseException the database exception
   */
  public int getFieldId(int projectId, int colNum) throws DatabaseException {
    String query =
        "SELECT FieldId FROM Field WHERE ProjectId = ? AND ColumnNumber = ?";

    int fieldId = 0;
    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, projectId);
      pstmt.setInt(2, colNum);

      try (ResultSet resultset = pstmt.executeQuery()) {

        if (!resultset.next()) {
          return 0;
        }

        fieldId = resultset.getInt("FieldId");

        return fieldId;
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      throw new DatabaseException(e);
    }
  }

  /**
   * Reads the.
   *
   * @param projectId the project id
   * @param title the title
   * @return the field
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
        returnField.setKnownData(resultset.getString("KnownData"));
        returnField.setHelpURL(resultset.getString("HelpURL"));
        returnField.setXCoord(resultset.getInt("XCoordinate"));
        returnField.setWidth(resultset.getInt("Width"));
        returnField.setColNum(resultset.getInt("ColumnNumber"));

        return returnField;
      }
    } catch (SQLException e) {
      throw new DatabaseException(
          "retrieving field with ProjectId " + projectId, e);
    }
  }

  /**
   * Updates the.
   *
   * @param field the field
   * @throws DatabaseException the database exception
   */
  public void update(Field field) throws DatabaseException {
    String query =
        "UPDATE Field SET KnownData = ?, HelpURL = ?, XCoordinate = ?, Width = ? "
            + "WHERE ProjectId = ? AND Title = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, field.getKnownData());
      pstmt.setString(2, field.getHelpURL());
      pstmt.setInt(3, field.getXCoord());
      pstmt.setInt(4, field.getWidth());
      pstmt.setString(5, field.getTitle());
      pstmt.setInt(6, field.getColNum());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException(
          "occurred updating field: " + field.getTitle(), e);
    }
  }
}

/**
 * ProjectDAO.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAO.
 */
public class ProjectDAO {

  /** The db. */
  private final Database db;

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger(Database.LOG_NAME);
  }

  /**
   * Instantiates a new project DAO.
   *
   * @param db the db
   */
  public ProjectDAO(Database db) {
    this.db = db;
  }

  public void initTable() throws DatabaseException {
    Statement stmt1 = null;
    Statement stmt2 = null;
    // @formatter:off
    final String dropProjectTable = "DROP TABLE IF EXISTS Project";
    final String createProjectTable =
        "CREATE TABLE Project ("
            + "ProjectId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
            + "Title TEXT NOT NULL, "
            + "RecordsPerBatch INTEGER NOT NULL, "
            + "FirstYCoord INTEGER NOT NULL, "
            + "RecordHeight INTEGER NOT NULL)"; // @formatter:on
    try {
      stmt1 = db.getConnection().createStatement();
      stmt1.executeUpdate(dropProjectTable);

      stmt2 = db.getConnection().createStatement();
      stmt2.executeUpdate(createProjectTable);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(stmt1);
      Database.closeSafely(stmt2);
    }
  }

  /**
   * Returns all Projects in an array.
   *
   * @return -> project array if found, else return null
   * @throws DatabaseException
   */
  public ArrayList<Project> getAll() throws DatabaseException {
    final ArrayList<Project> allProjects = new ArrayList<Project>();
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    try {
      final String selectsql = "SELECT * from Project";
      pstmt = db.getConnection().prepareStatement(selectsql);

      resultset = pstmt.executeQuery();
      while (resultset.next()) {
        final Project resultProject = new Project();

        resultProject.setProjectId(resultset.getInt("projectId"));
        resultProject.setTitle(resultset.getString("Title"));
        resultProject.setRecordsPerBatch(resultset.getInt("RecordsPerBatch"));
        resultProject.setFirstYCoord(resultset.getInt("FirstYCoord"));
        resultProject.setRecordHeight(resultset.getInt("RecordHeight"));

        allProjects.add(resultProject);
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return allProjects;
  }

  /**
   * Inserts the given project to the database.
   *
   * @param project the project
   * @return the int
   */
  public int create(Project project) throws DatabaseException {
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet resultset = null;
    try {
      final String insertsql =
          "INSERT INTO Project (" + "Title, RecordsPerBatch, FirstYCoord, RecordHeight)"
              + "VALUES (?, ?, ?, ?)";
      pstmt = db.getConnection().prepareStatement(insertsql);

      pstmt.setString(1, project.getTitle());
      pstmt.setInt(2, project.getRecordsPerBatch());
      pstmt.setInt(3, project.getFirstYCoord());
      pstmt.setInt(4, project.getRecordHeight());

      if (pstmt.executeUpdate() == 1) {
        stmt = db.getConnection().createStatement();
        resultset = stmt.executeQuery("SELECT last_insert_rowid()");
        resultset.next();
        final int projectId = resultset.getInt(1);
        project.setProjectId(projectId);
      } else {
        throw new DatabaseException("Unable to insert new project into database.");
      }
    } catch (final SQLException e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    return project.getProjectId();
  }

  /**
   * Gets the project.
   *
   * @param id the id
   * @return the project
   */
  public Project read(int projectid) throws DatabaseException {
    PreparedStatement pstmt = null;
    ResultSet resultset = null;
    final Project resultProject = new Project();
    try {
      final String selectsql = "SELECT * from Project WHERE projectId = ?";
      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, projectid);

      resultset = pstmt.executeQuery();

      resultset.next();
      resultProject.setProjectId(resultset.getInt(1));
      resultProject.setTitle(resultset.getString(2));
      resultProject.setRecordsPerBatch(resultset.getInt(3));
      resultProject.setFirstYCoord(resultset.getInt(4));
      resultProject.setRecordHeight(resultset.getInt(5));
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
      Database.closeSafely(resultset);
    }
    if (resultProject.getTitle() == "") {
      return null;
    }
    return resultProject;
  }

  /**
   * Deletes the specified project.
   *
   * @param project the project
   * @throws DatabaseException
   */
  public void delete(Project project) throws DatabaseException {
    PreparedStatement pstmt = null;
    try {
      final String selectsql = "DELETE from Project WHERE projectId = ?";

      pstmt = db.getConnection().prepareStatement(selectsql);
      pstmt.setInt(1, project.getProjectId());

      pstmt.executeUpdate();
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    } finally {
      Database.closeSafely(pstmt);
    }
  }
}

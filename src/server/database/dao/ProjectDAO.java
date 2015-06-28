
package server.database.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.Database;
import server.database.DatabaseException;

import shared.model.Project;


public class ProjectDAO {

  
  private Database      db;

  
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  
  public ProjectDAO(Database db) {
    this.db = db;
  }

  
  public void initTable() throws DatabaseException {
    String dropTable = "DROP TABLE IF EXISTS Project";// @formatter:off
      String createTable =
          "CREATE TABLE Project ("
          + "ProjectId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
          + "Title TEXT NOT NULL, "
          + "RecordsPerBatch INTEGER NOT NULL, "
          + "FirstYCoord INTEGER NOT NULL, "
          + "RecordHeight INTEGER NOT NULL)"; // @formatter:on
    try (Statement stmt1 = db.getConnection().createStatement();
        Statement stmt2 = db.getConnection().createStatement()) {
      stmt1.executeUpdate(dropTable);

      stmt2.executeUpdate(createTable);
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public ArrayList<Project> getAll() throws DatabaseException {
    String query = "SELECT * from Project";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {

      try (ResultSet resultset = pstmt.executeQuery()) {

        ArrayList<Project> allProjectes = new ArrayList<Project>();
        while (resultset.next()) {
          Project resultProject = new Project();

          resultProject.setProjectId(resultset.getInt("ProjectId"));
          resultProject.setTitle(resultset.getString("Title"));
          resultProject.setRecordsPerBatch(resultset.getInt("RecordsPerBatch"));
          resultProject.setFirstYCoord(resultset.getInt("FirstYCoord"));
          resultProject.setRecordHeight(resultset.getInt("RecordHeight"));

          allProjectes.add(resultProject);
        }
        return allProjectes;
      }
    } catch (SQLException e) {
      throw new DatabaseException(e);
    }
  }

  
  public int create(Project newProject) throws DatabaseException {
    String query =
        "INSERT INTO Project (Title, RecordsPerBatch, FirstYCoord, RecordHeight)"
            + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setString(1, newProject.getTitle());
      pstmt.setInt(2, newProject.getRecordsPerBatch());
      pstmt.setInt(3, newProject.getFirstYCoord());
      pstmt.setInt(4, newProject.getRecordHeight());

      if (pstmt.executeUpdate() == 1) {
        try (Statement stmt = db.getConnection().createStatement();
            ResultSet resultset = stmt.executeQuery("SELECT last_insert_rowid()")) {
          resultset.next();
          int id = resultset.getInt(1);
          newProject.setProjectId(id);
        }
      } else {
        throw new DatabaseException("ERROR occurred while inserting project: "
            + newProject.toString());
      }
    } catch (SQLException e) {
      throw new DatabaseException("occurred while inserting project: " + newProject.toString());
    }
    return newProject.getProjectId();
  }

  
  public Project read(int projectId) throws DatabaseException {
    String query = "SELECT * FROM Project WHERE ProjectId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, projectId);

      try (ResultSet resultset = pstmt.executeQuery()) {
        Project resultProject = new Project();

        if (!resultset.next()) {
          return null;
        }

        resultProject.setProjectId(resultset.getInt("ProjectId"));
        resultProject.setTitle(resultset.getString("Title"));
        resultProject.setRecordsPerBatch(resultset.getInt("RecordsPerBatch"));
        resultProject.setFirstYCoord(resultset.getInt("FirstYCoord"));
        resultProject.setRecordHeight(resultset.getInt("RecordHeight"));

        // if (resultset.next())
        // throw new DatabaseException("Readore than one project: " + projectId
        // + " from database...");

        return resultProject;
      }
    } catch (SQLException e) {
      throw new DatabaseException("retrieving project with ProjectId " + projectId, e);
    }
  }

  
  public void delete(Project project) throws DatabaseException {
    String query = "DELETE from Project WHERE ProjectId = ?";

    try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
      pstmt.setInt(1, project.getProjectId());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new DatabaseException("deleting project with projectID: " + project.getProjectId(), e);
    }
  }

}

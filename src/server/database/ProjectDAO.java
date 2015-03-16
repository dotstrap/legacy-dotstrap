package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAO.
 */
public class ProjectDAO {

    /** The db. */
    private Database      db;

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    /**
     * Instantiates a new project DAO.
     *
     * @param db
     *            the db
     */
    public ProjectDAO(Database db) {
        this.db = db;
    }

    /**
     * Returns all Projects in an array.
     *
     * @return -> project array if found, else return null
     * @throws DatabaseException
     */
    public ArrayList<Project> getAll() throws DatabaseException {
        logger.entering("server.database.ProjectDAO", "getAll");

        ArrayList<Project> allProjects = new ArrayList<Project>();
        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        try {
            String selectsql = "SELECT * from Project";
            pstmt = db.getConnection().prepareStatement(selectsql);

            resultset = pstmt.executeQuery();
            while (resultset.next()) {
                Project resultProject = new Project();

                resultProject.setID(resultset.getInt("ID"));
                resultProject.setTitle(resultset.getString("Title"));
                resultProject.setRecordsPerBatch(resultset
                        .getInt("RecordsPerBatch"));
                resultProject.setFirstYCoord(resultset.getInt("FirstYCoord"));
                resultProject.setRecordHeight(resultset.getInt("RecordHeight"));

                allProjects.add(resultProject);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.ProjectDAO", "getAll");
        return allProjects;
    }

    /**
     * Inserts the given project to the database.
     *
     * @param project
     *            the project
     * @return the int
     */
    public int create(Project project) throws DatabaseException {
        logger.entering("server.database.ProjectDAO", "create");

        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        try {
            String insertsql = "INSERT INTO Project (Title, RecordsPerBatch, FirstYCoord, RecordHeight)"
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
                int id = resultset.getInt(1);
                project.setID(id);
            } else {
                throw new DatabaseException(
                        "Unable to insert new project into database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.ProjectDAO", "create");
        return project.getID();
    }

    /**
     * Gets the project.
     *
     * @param id
     *            the id
     * @return the project
     */
    public Project read(int id) throws DatabaseException {
        logger.entering("server.database.ProjectDAO", "read");

        PreparedStatement pstmt = null;
        ResultSet resultset = null;
        Project resultProject = new Project();
        try {
            String selectsql = "SELECT * from Project WHERE ID = ?";
            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, id);

            resultset = pstmt.executeQuery();

            resultset.next();
            resultProject.setID(resultset.getInt(1));
            resultProject.setTitle(resultset.getString(2));
            resultProject.setRecordsPerBatch(resultset.getInt(3));
            resultProject.setFirstYCoord(resultset.getInt(4));
            resultProject.setRecordHeight(resultset.getInt(5));
        }
         catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            Database.closeSafely(pstmt);
            Database.closeSafely(resultset);
        }

        logger.exiting("server.database.ProjectDAO", "read");
        if (resultProject.getTitle() == "") {
            return null;
        }
        return resultProject;
    }

    /**
     * Deletes the specified project.
     *
     * @param project
     *            the project
     * @throws DatabaseException
     */
    public void delete(Project project) throws DatabaseException {
        logger.entering("server.database.ProjectDAO", "delete");

        PreparedStatement pstmt = null;
        try {
            String selectsql = "DELETE from Project WHERE ID = ?";

            pstmt = db.getConnection().prepareStatement(selectsql);
            pstmt.setInt(1, project.getID());

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

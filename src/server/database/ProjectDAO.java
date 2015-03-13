package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectDAO.
 */
public class ProjectDAO {

    /** The db. */
    private Database db;

    /**
     * Instantiates a new project dao.
     *
     * @param db
     *            the db
     */
    public ProjectDAO(Database db) {
        this.db = db;
    }
}

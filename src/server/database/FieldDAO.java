package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldDAO.
 */
public class FieldDAO {

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
}

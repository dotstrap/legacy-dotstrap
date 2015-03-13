package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordDAO.
 */
public class RecordDAO {

    /** The db. */
    private Database db;

    /**
     * Instantiates a new record dao.
     *
     * @param db
     *            the db
     */
    public RecordDAO(Database db) {
        this.db = db;
    }
}

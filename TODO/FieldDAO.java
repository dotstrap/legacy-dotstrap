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

    /**
     * Gets the field.
     *
     * @param title
     *            the title
     * @return the field
     */
    public Field getField(String title) {
        return null;
    }

    /**
     * Gets the all.
     *
     * @return the all
     */
    public ArrayList<Field> getAll() {
        ArrayList<Field> fields = new ArrayList<Field>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultset = null;

        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Field";
            pstmt = connection.prepareStatement(selectsql);

            resultset = pstmt.executeQuery();

            while (resultset.next()) {
                Field returnField = new Field();
                returnField.setID(resultset.getInt(1));
                returnField.setProjectID(resultset.getInt(2));
                returnField.setHelp(resultset.getString(3));
                returnField.setKnownPath(resultset.getString(4));
                returnField.setWidth(resultset.getInt(5));
                returnField.setX(resultset.getInt(6));
                returnField.setTitle(resultset.getString(7));
                fields.add(returnField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
        resultset = null;

        return fields;
    }

    /**
     * Adds the.
     *
     * @param field
     *            the field
     * @return the int
     */
    public int add(Field field) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO field (ProjectID, FieldPath, KnownPath, Width, XCoordinate, Title)"
                               + "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertsql);
            pstmt.setInt(1, field.getProjectID());
            pstmt.setString(2, field.getHelp());
            pstmt.setString(3, field.getKnownPath());
            pstmt.setInt(4, field.getWidth());
            pstmt.setInt(5, field.getX());
            pstmt.setString(6, field.getTitle());

            if (pstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                field.setID(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }

    /**
     * Delete.
     *
     * @param field
     *            the field
     */
    public void delete(Field field) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Field WHERE ID = ?";
            pstmt = connection.prepareStatement(selectsql);
            pstmt.setInt(1, field.getID());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        pstmt = null;
    }
}

/**
 * FieldDAO.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
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
     * @param db the db
     */
    public FieldDAO(Database db) {
        this.db = db;
    }
    
    /**
     * Adds the.
     *
     * @param field the field
     * @return the int
     */
    public int add(Field field) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO field (ProjectID, FieldPath, KnownPath, Width, XCoordinate, Title)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setInt(1, field.getProjectID());
            SQLstmt.setString(2, field.getHelp());
            SQLstmt.setString(3, field.getKnownPath());
            SQLstmt.setInt(4, field.getWidth());
            SQLstmt.setInt(5, field.getX());
            SQLstmt.setString(6, field.getTitle());
            
            if (SQLstmt.executeUpdate() == 1) {
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
        SQLstmt = null;
        stmt = null;
        resultset = null;
        return id;
    }
    
    /**
     * Delete.
     *
     * @param field the field
     */
    public void delete(Field field) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Field WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, field.getID());
            
            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = null;
        SQLstmt = null;
    }
    
    public ArrayList<Field> getAll() {
        ArrayList<Field> fields = new ArrayList<Field>();
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Field";
            SQLstmt = connection.prepareStatement(selectsql);
            
            resultset = SQLstmt.executeQuery();
            
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
        SQLstmt = null;
        resultset = null;
        
        return fields;
    }
    
    /**
     * Gets the field.
     *
     * @param title the title
     * @return the field
     */
    public Field getField(String title) {
        return null;
    }
}

/**
 * ProjectDAO.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
import java.util.ArrayList;

import shared.model.Project;
import shared.model.ProjectInfo;

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
     * @param db the db
     */
    public ProjectDAO(Database db) {
        this.db = db;
    }
    
    /**
     * Adds the.
     *
     * @param project the project
     * @return the int
     */
    public int add(Project project) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        Statement stmt = null;
        ResultSet resultset = null;
        int id = -1;
        try {
            connection = db.getConnection();
            String insertsql = "INSERT INTO Project (Name, RecordsPerBatch, FirstYCoord, RecordHeight)"
                    + "VALUES (?, ?, ?, ?)";
            SQLstmt = connection.prepareStatement(insertsql);
            SQLstmt.setString(1, project.getProjInfo().getName());
            SQLstmt.setInt(2, project.getRecordsPerBatch());
            SQLstmt.setInt(3, project.getFirstY());
            SQLstmt.setInt(4, project.getRecordHeight());
            
            if (SQLstmt.executeUpdate() == 1) {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SELECT last_insert_rowid()");
                resultset.next();
                id = resultset.getInt(1);
                project.getProjInfo().setID(id);
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
     * @param project the project
     */
    public void delete(Project project) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        try {
            connection = db.getConnection();
            String selectsql = "DELETE from Project WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, project.getProjInfo().getID());
            
            SQLstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        connection = null;
        SQLstmt = null;
    }
    
    public ArrayList<Project> getAll() {
        ArrayList<Project> projects = new ArrayList<Project>();
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Project";
            SQLstmt = connection.prepareStatement(selectsql);
            
            resultset = SQLstmt.executeQuery();
            
            while (resultset.next()) {
                Project returnProject = new Project();
                ProjectInfo p = new ProjectInfo();
                p.setID(resultset.getInt(1));
                p.setName(resultset.getString(2));
                returnProject.setProjInfo(p);
                returnProject.setRecordsPerBatch(resultset.getInt(3));
                returnProject.setFirstY(resultset.getInt(4));
                returnProject.setRecordHeight(resultset.getInt(5));
                
                projects.add(returnProject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        connection = null;
        SQLstmt = null;
        resultset = null;
        
        return projects;
    }
    
    /**
     * Gets the project.
     *
     * @param id the id
     * @return the project
     */
    public Project getProject(int id) {
        Connection connection = null;
        PreparedStatement SQLstmt = null;
        ResultSet resultset = null;
        Project returnProject = new Project();
        try {
            connection = db.getConnection();
            String selectsql = "SELECT * from Project WHERE ID = ?";
            SQLstmt = connection.prepareStatement(selectsql);
            SQLstmt.setInt(1, id);
            
            resultset = SQLstmt.executeQuery();
            
            resultset.next();
            ProjectInfo pi = new ProjectInfo();
            pi.setID(id);
            pi.setName(resultset.getString(2));
            returnProject.setProjInfo(pi);
            returnProject.setRecordsPerBatch(resultset.getInt(3));
            returnProject.setFirstY(resultset.getInt(4));
            returnProject.setRecordHeight(resultset.getInt(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        connection = null;
        SQLstmt = null;
        resultset = null;
        if (returnProject.getProjInfo().getName() == "") {
            return null;
        }
        return returnProject;
    }
}

/**
 * Database.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Database.
 */
public class Database {
    
    /** The connection. */
    private static Connection connection;
    
    /** The logger. */
    private static Logger     logger    = Logger.getLogger("server");
    
    final String              currClass = Database.class.getName();
    
    /** The batch dao. */
    private BatchDAO          batchDAO;
    /** The field dao. */
    private FieldDAO          fieldDAO;
    /** The project dao. */
    private ProjectDAO        projectDAO;
    /** The record dao. */
    private RecordDAO         recordDAO;
    /** The user dao. */
    private UserDAO           userDAO;
    
    /**
     * Instantiates a new database.
     */
    public Database() {
        userDAO = new UserDAO(this);
        batchDAO = new BatchDAO(this);
        fieldDAO = new FieldDAO(this);
        projectDAO = new ProjectDAO(this);
        recordDAO = new RecordDAO(this);
        connection = null;
    }
    
    /**
     * End transaction.
     *
     * @param commit the commit
     */
    public void endTransaction(boolean commit) {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
        // Commit or rollback the transaction and close the connection
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        connection = null;
        logger.exiting(currClass, currMethod);
    }
    
    public BatchDAO getBatchDAO() {
        return batchDAO;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public FieldDAO getFieldDAO() {
        return fieldDAO;
    }
    
    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }
    
    public RecordDAO getRecordDAO() {
        return recordDAO;
    }
    
    public UserDAO getUserDAO() {
        return userDAO;
    }
    
    /**
     * init.
     *
     * @throws DatabaseException the database exception
     */
    public void init() throws DatabaseException {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }
        
        logger.exiting(currClass, currMethod);
    }
    
    /**
     * init tables.
     */
    public void initTables() {
        PreparedStatement SQLstmt = null;
        String dbName = "database/indexer_server.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
        initBatch(SQLstm);
        initRecord();
        initField();
        initProject();
        initUser();
    }
    
    /**
     * Start transaction.
     *
     * @throws DatabaseException the database exception
     */
    public void startTransaction(PreparedStatement SQLstmt, String dbName,
            String connectionURL) throws DatabaseException {
        
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
        // Open a connection to the database and start a transaction
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        
        logger.exiting(currClass, currMethod);
    }
    
    /**
     * init batch.
     */
    private void initBatch() {
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS Batch";
            String sql2 = "CREATE TABLE Batch (ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,"
                    + "FilePath TEXT NOT NULL , ProjectID INTEGER NOT NULL ,State INTEGER NOT NULL)";
            SQLstmt = connection.prepareStatement(sql);
            SQLstmt.executeUpdate();
            
            SQLstmt = connection.prepareStatement(sql2);
            SQLstmt.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        SQLstmt = null;
        connection = null;
        logger.exiting(currClass, currMethod);
        
    }
    
    /**
     * init field.
     */
    private void initField() {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS Field";
            String sql2 = "CREATE TABLE Field (ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,"
                    + "ProjectID INTEGER NOT NULL , FieldPath TEXT NOT NULL , KnownPath TEXT NOT NULL ,"
                    + "Width INTEGER NOT NULL , XCoordinate INTEGER NOT NULL , Title TEXT NOT NULL   )";
            
            SQLstmt = connection.prepareStatement(sql);
            SQLstmt.executeUpdate();
            
            SQLstmt = connection.prepareStatement(sql2);
            SQLstmt.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        SQLstmt = null;
        connection = null;
        
        logger.exiting(currClass, currMethod);
    }
    
    /**
     * init project.
     */
    private void initProject() {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS Project";
            String sql2 = "CREATE TABLE Project (ID INTEGER PRIMARY KEY  NOT NULL , Name TEXT NOT NULL ,"
                    + "RecordsPerBatch INTEGER NOT NULL , FirstYCoord INTEGER, RecordHeight INTEGER)";
            SQLstmt = connection.prepareStatement(sql);
            SQLstmt.executeUpdate();
            
            SQLstmt = connection.prepareStatement(sql2);
            SQLstmt.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR inside: " + currMethod);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        SQLstmt = null;
        connection = null;
        
        logger.exiting(currClass, currMethod);
    }
    
    /**
     * init record.
     */
    private void initRecord() {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS Record";
            String sql2 = "CREATE TABLE Record (ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
                    + "RowOnImage INTEGER NOT NULL , BatchID INTEGER NOT NULL , Data TEXT NOT NULL , FieldID INTEGER NOT NULL )";
            SQLstmt = connection.prepareStatement(sql);
            SQLstmt.executeUpdate();
            
            SQLstmt = connection.prepareStatement(sql2);
            SQLstmt.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLstmt = null;
        connection = null;
        logger.exiting(currClass, currMethod);
    }
    
    /**
     * init user.
     */
    private void initUser() {
        String currMethod = Database.class.getEnclosingMethod().getName();
        logger.entering(currClass, currMethod);
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS User";
            String sql2 = "CREATE TABLE User (ID INTEGER PRIMARY KEY  NOT NULL  UNIQUE , "
                    + "username TEXT NOT NULL  UNIQUE , password TEXT NOT NULL , "
                    + "FirstName TEXT NOT NULL , LastName TEXT NOT NULL , Email TEXT NOT NULL  UNIQUE , "
                    + "RecordCount INTEGER NOT NULL , CurrentBatch INTEGER NOT NULL )";
            SQLstmt = connection.prepareStatement(sql);
            SQLstmt.executeUpdate();
            
            SQLstmt = connection.prepareStatement(sql2);
            SQLstmt.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQLstmt = null;
        connection = null;
        logger.exiting(currClass, currMethod);
    }
}

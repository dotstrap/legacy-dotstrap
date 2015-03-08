/**
 * Database.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.sql.*;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Database.
 */
public class Database {
    
    /** The connection. */
    private static Connection connection;
    
    /** The logger. */
    private static Logger     logger;
    
    static {
        logger = Logger.getLogger("recordindexer");
    }
    
    /**
     * Initialize.
     *
     * @throws DatabaseException the database exception
     */
    public static void initialize() throws DatabaseException {
        logger.entering("server.database.Database", "initialize");
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // ERROR! Could not load database driver
            System.out.println("Exception thrown in database.initialize");
            e.printStackTrace();
        }
        logger.exiting("server.database.Database", "initialize");
    }
    
    /**
     * Initialize batch.
     */
    private static void initializeBatch() {
        PreparedStatement SQLstmt = null;
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
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
            e.printStackTrace();
        }
        SQLstmt = null;
        connection = null;
    }
    
    /**
     * Initialize field.
     */
    private static void initializeField() {
        PreparedStatement SQLstmt = null;
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
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
            e.printStackTrace();
        }
        SQLstmt = null;
        connection = null;
    }
    
    /**
     * Initialize project.
     */
    private static void initializeProject() {
        PreparedStatement SQLstmt = null;
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
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
            e.printStackTrace();
        }
        SQLstmt = null;
        connection = null;
    }
    
    /**
     * Initialize record.
     */
    private static void initializeRecord() {
        PreparedStatement SQLstmt = null;
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
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
    }
    
    /**
     * Initialize tables.
     */
    public static void initializeTables() {
        initializeBatch();
        initializeRecord();
        initializeField();
        initializeProject();
        initializeUser();
    }
    
    /**
     * Initialize user.
     */
    private static void initializeUser() {
        PreparedStatement SQLstmt = null;
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        
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
    }
    
    /** The batch dao. */
    private BatchDAO   batchDAO;
    
    /** The field dao. */
    private FieldDAO   fieldDAO;
    
    /** The project dao. */
    private ProjectDAO projectDAO;
    
    /** The record dao. */
    private RecordDAO  recordDAO;
    
    /** The user dao. */
    private UserDAO    userDAO;
    
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
        logger.entering("server.database.Database", "endTransaction");
        // Commit or rollback the transaction and close the connection
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            // ERROR
            // e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out
                .println("Exception thrown in database.endTransaction.closeConnection");
                e.printStackTrace();
            }
        }
        connection = null;
        logger.exiting("server.database.Database", "endTransaction");
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
     * Start transaction.
     *
     * @throws DatabaseException the database exception
     */
    public void startTransaction() throws DatabaseException {
        logger.entering("server.database.Database", "startTransaction");
        // Open a connection to the database and start a transaction
        String dbName = "Apache.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        try {
            // Open a database connection
            connection = DriverManager.getConnection(connectionURL);
            // Start a transaction
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            // ERROR
            e.printStackTrace();
        }
        
        logger.exiting("server.database.Database", "startTransaction");
    }
}

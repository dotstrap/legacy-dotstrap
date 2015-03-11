/**
 * Database.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
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

    /** The database driver connection. */
    private static Connection connection;
    /** The logger used throughout the project. */
    private static Logger     logger;

    // DataBase Access /////////////
    /**
     * The batch DataBaseAccess.
     * interfaces with the database to modify the batch (image) table
     */
    private BatchDAO          batchDAO;
    /**
     * The field DataBaseAccess.
     * interfaces with the database to modify the field table
     */
    private FieldDAO          fieldDAO;
    /**
     * The project DataBaseAccess.
     * interfaces with the database to modify the project table
     */
    private ProjectDAO        projectDAO;
    /**
     * The record DataBaseAccess.
     * interfaces with the database to modify the record table
     */
    private RecordDAO         recordDAO;
    /**
     * The user DataBaseAccess.
     * interfaces with the database to modify the user table
     */
    private UserDAO           userDAO;

    /**
     * Instantiates a new database.
     */
    public Database() {
        connection = null;

        logger = Logger.getLogger("server");

        batchDAO = new BatchDAO(this);
        fieldDAO = new FieldDAO(this);
        projectDAO = new ProjectDAO(this);
        recordDAO = new RecordDAO(this);
        userDAO = new UserDAO(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public BatchDAO getBatchDAO() {
        return batchDAO;
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

    // TODO when to call this?
    /**
     * Initializes the Java SQL driver.
     *
     * @throws DatabaseException the database exception
     */
    public static void initDriver() throws DatabaseException {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
    }

    /**
     * Starts the transaction.
     *
     * @throws DatabaseException the database exception
     */
    public void startTransaction() throws DatabaseException {

        logger.entering(this.getClass().toString(), this.getClass()
                .getEnclosingMethod().getName());

        String dbName = "database/indexer_server.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        // Open a connection to the database and start a transaction
        try {
            assert (connection == null);
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        logger.exiting(this.getClass().toString(), this.getClass()
                .getEnclosingMethod().getName());
    }

    /**
     * Ends the database transaction.
     *
     * @param shouldCommit - commit or rollback transaction?
     * @throws DatabaseException the database exception
     */
    public void endTransaction(boolean shouldCommit) throws DatabaseException {
        // Commit or rollback the transaction and finally close the connection
        try {
            if (shouldCommit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getMessage(), e.getCause());
                logger.info(e.getStackTrace().toString());
            }
        }
        connection = null;
    }

    /**
     * Initializes the database
     * by sequentially dropping each table and then creating it.
     */
    public void initDBTables() {
        String dropBatchTable = "DROP TABLE IF EXISTS Batch";
        String createBatchTable = "CREATE TABLE Batch (ID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL UNIQUE,"
                + "FilePath TEXT NOT NULL, ProjectID INTEGER NOT NULL, State INTEGER NOT NULL)";

        String dropFieldTable = "DROP TABLE IF EXISTS Field";
        String createFieldTable = "CREATE TABLE Field ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, ProjectID INTEGER NOT NULL, FieldPath TEXT NOT NULL,"
                + "KnownPath TEXT NOT NULL, Width INTEGER NOT NULL, XCoordinate INTEGER NOT NULL, Title TEXT NOT NULL)";

        String dropProjectTable = "DROP TABLE IF EXISTS Project";
        String createProjectTable = "CREATE TABLE Project ("
                + "ID INTEGER PRIMARY KEY  NOT NULL, Name TEXT NOT NULL,"
                + "RecordsPerBatch INTEGER NOT NULL, FirstYCoord INTEGER, RecordHeight INTEGER)";

        String dropRecordTable = "DROP TABLE IF EXISTS Record";
        String createRecordTable = "CREATE TABLE Record (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
                + "RowOnImage INTEGER NOT NULL, BatchID INTEGER NOT NULL, Data TEXT NOT NULL, FieldID INTEGER NOT NULL)";

        String dropUserTable = "DROP TABLE IF EXISTS User";
        String createUserTable = "CREATE TABLE User (ID INTEGER PRIMARY KEY NOT NULL UNIQUE,"
                + "username TEXT NOT NULL UNIQUE, password TEXT NOT NULL,"
                + "FirstName TEXT NOT NULL LastName TEXT NOT NULL, Email TEXT NOT NULL UNIQUE,"
                + "RecordCount INTEGER NOT NULL, CurrentBatch INTEGER NOT NULL)";

        initTable(dropBatchTable, createBatchTable);
        initTable(dropFieldTable, createFieldTable);
        initTable(dropProjectTable, createProjectTable);
        initTable(dropRecordTable, createRecordTable);
        initTable(dropUserTable, createUserTable);
    }

    /**
     * Initializes the given table.
     *
     * @param dropStmt the SQL statement to drop the given table
     * @param createStmt the the SQL statement to create the given table
     */
    private void initTable(String dropStmt, String createStmt) {
        logger.entering(this.getClass().toString(), Database.class
                .getEnclosingMethod().getName());

        PreparedStatement pstmt = null;
        try {

            try {
                startTransaction();
            } catch (DatabaseException e) {
                logger.log(Level.SEVERE, e.getMessage(), e.getCause());
                logger.info(e.getStackTrace().toString());
            }

            pstmt = connection.prepareStatement(dropStmt);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement(createStmt);
            pstmt.executeUpdate();

            try {
                endTransaction(true);
            } catch (DatabaseException e) {
                logger.log(Level.SEVERE, e.getMessage(), e.getCause());
                logger.info(e.getStackTrace().toString());
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e.getCause());
            logger.info(e.getStackTrace().toString());
        }
        pstmt = null;

        logger.exiting(this.getClass().toString(), this.getClass()
                .getEnclosingMethod().getName());
    }

}

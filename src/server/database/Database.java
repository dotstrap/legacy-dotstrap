/**
 * Database.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
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
    /** The logger used throughout the project. */
    private static Logger     logger;
    static {
        logger = Logger.getLogger("server");
    }

    // DataBase Access /////////////
    /** The database driver connection. */
    private Connection    connection;

    /**
     * The batch DataBaseAccess. interfaces with the database to modify the
     * batch (image) table
     */
    private BatchDAO      batchDAO;
    /**
     * The field DataBaseAccess. interfaces with the database to modify the
     * field table
     */
    private FieldDAO      fieldDAO;
    /**
     * The project DataBaseAccess. interfaces with the database to modify the
     * project table
     */
    private ProjectDAO    projectDAO;
    /**
     * The record DataBaseAccess. interfaces with the database to modify the
     * record table
     */
    private RecordDAO     recordDAO;
    /**
     * The user DataBaseAccess. interfaces with the database to modify the user
     * table
     */
    private UserDAO       userDAO;

    /**
     * Instantiates a new database.
     */
    public Database() {
        connection = null;

        batchDAO   = new BatchDAO(this);
        fieldDAO   = new FieldDAO(this);
        projectDAO = new ProjectDAO(this);
        recordDAO  = new RecordDAO(this);
        userDAO    = new UserDAO(this);
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

    // TODO: how to make these into one generic method?
    public static void closeSafely(Connection c) {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.toString());
                logger.log(Level.FINE, "STACKTRACE: ", e);
            }
        }
    }

    public static void closeSafely(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.toString());
                logger.log(Level.FINE, "STACKTRACE: ", e);
            }
        }
    }

    public static void closeSafely(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.toString());
                logger.log(Level.FINE, "STACKTRACE: ", e);
            }
        }
    }

    public static void closeSafely(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.toString());
                logger.log(Level.FINE, "STACKTRACE: ", e);
            }
        }
    }

    /**
     * Initializes the Java SQL driver.
     *
     * @throws DatabaseException
     *             the database exception
     */
    public static void initDriver() throws DatabaseException {
        logger.entering("server.database.Database", "initDriver");

        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        logger.exiting("server.database.Database", "initDriver");
    }

    /**
     * Starts the transaction.
     *
     * @throws DatabaseException
     *             the database exception
     */
    public void startTransaction() throws DatabaseException {
        logger.entering("server.database.Database", "startTransaction");

        String dbDir = "database";
        String dbFile = dbDir + "/indexer_server.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbFile;
        // Open a connection to the database and start a transaction
        try {
            assert (connection == null);
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        logger.exiting("server.database.Database", "startTransaction");
    }

    /**
     * Ends the database transaction.
     *
     * @param shouldCommit
     *            - commit or rollback transaction?
     * @throws DatabaseException
     *             the database exception
     */
    public void endTransaction(boolean shouldCommit) throws DatabaseException {
        // Commit or rollback the transaction and finally close the connection
        logger.entering("server.database.Database", "endTransaction");

        try {
            if (shouldCommit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            closeSafely(connection);
        }

        logger.exiting("server.database.Database", "endTransaction");
    }

    /**
     * Initializes the database by sequentially dropping each table and then
     * creating it.
     *
     * @throws DatabaseException
     */
    public void initTables() throws DatabaseException {
        logger.entering("server.database.Database", "initTables");

        String dropBatchTable = "DROP TABLE IF EXISTS Batch";
        String createBatchTable = "CREATE TABLE Batch ("
                + "BatchID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
                + "FilePath TEXT NOT NULL, "
                + "ProjectID INTEGER NOT NULL, "
                + "Status INTEGER NOT NULL)";

        String dropFieldTable = "DROP TABLE IF EXISTS Field";
        String createFieldTable = "CREATE TABLE Field ("
                + "FieldID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "ProjectID INTEGER NOT NULL, "
                + "Title TEXT NOT NULL"
                + "KnownData TEXT NOT NULL"
                + "HelpURL TEXT NOT NULL, "
                + "FieldPath TEXT NOT NULL, "
                + "XCoordinate INTEGER NOT NULL, "
                + "Width INTEGER NOT NULL, "
                + "ColumnNumber INTEGER NOT NULL)";

        String dropProjectTable = "DROP TABLE IF EXISTS Project";
        String createProjectTable = "CREATE TABLE Project ("
                + "ProjectID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "Title TEXT NOT NULL, "
                + "RecordsPerBatch INTEGER NOT NULL, "
                + "FirstYCoord INTEGER, "
                + "RecordHeight INTEGER)";

        String dropRecordTable = "DROP TABLE IF EXISTS Record";
        String createRecordTable = "CREATE TABLE Record ("
                + "RecordID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "FieldID INTEGER NOT NULL, "
                + "BatchID INTEGER NOT NULL, "
                + "BatchURL TEXT NOT NULL, "
                + "Data TEXT NOT NULL COllATE NOCASE,"
                + "RowNumber INTEGER NOT NULL, "
                + "ColumnNumber INTEGER NOT NULL)";

        String dropUserTable = "DROP TABLE IF EXISTS User";
        String createUserTable = "CREATE TABLE User ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                + "Username TEXT NOT NULL UNIQUE, "
                + "Password TEXT NOT NULL, "
                + "FirstName TEXT NOT NULL, "
                + "LastName TEXT NOT NULL, "
                + "Email TEXT NOT NULL UNIQUE, "
                + "RecordCount INTEGER NOT NULL, "
                + "CurrentBatchID INTEGER NOT NULL)";

        try {
            initCurrTable(dropBatchTable, createBatchTable);
            initCurrTable(dropFieldTable, createFieldTable);
            initCurrTable(dropBatchTable, createBatchTable);
            initCurrTable(dropProjectTable, createProjectTable);
            initCurrTable(dropRecordTable, createRecordTable);
            initCurrTable(dropUserTable, createUserTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        logger.exiting("server.database.Database", "initTables");
    }

    /**
     * Initializes the given table.
     *
     * @param dropStmt
     *            the SQL statement to drop the given table
     * @param createStmt
     *            the SQL statement to create the given table
     */
    private void initCurrTable(String dropStmt, String createStmt)
            throws DatabaseException {
        logger.entering("test.server.database.Database", "initTable");

        Statement stmt1 = null;
        Statement stmt2 = null;
        try {
            stmt1 = connection.createStatement();
            stmt1.executeUpdate(dropStmt);

            stmt2 = connection.createStatement();
            stmt2.executeUpdate(createStmt);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            closeSafely(stmt1);
            closeSafely(stmt2);
        }

        logger.exiting("test.server.database.Database", "initTable");
    }
}

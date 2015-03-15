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
    private Connection connection;

    /**
     * The batch DataBaseAccess. interfaces with the database to modify the
     * batch (image) table
     */
    private BatchDAO          batchDAO;
    /**
     * The field DataBaseAccess. interfaces with the database to modify the
     * field table
     */
    private FieldDAO          fieldDAO;
    /**
     * The project DataBaseAccess. interfaces with the database to modify the
     * project table
     */
    private ProjectDAO        projectDAO;
    /**
     * The record DataBaseAccess. interfaces with the database to modify the
     * record table
     */
    private RecordDAO         recordDAO;
    /**
     * The user DataBaseAccess. interfaces with the database to modify the user
     * table
     */
    private UserDAO           userDAO;

    /**
     * Instantiates a new database.
     */
    public Database() {
        connection = null;

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

        //if (connection != null) {
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
       //}

        logger.exiting("server.database.Database", "endTransaction");
    }

    /**
     * Initializes the database by sequentially dropping each table and then
     * creating it.
     *
     * @throws DatabaseException
     */
    public void initTables() throws DatabaseException {
        logger.entering("server.database.Database", "initDBTables");

        String dropBatchTable = "DROP TABLE IF EXISTS Batch";
        String createBatchTable = "CREATE TABLE Batch ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
                + "FilePath TEXT NOT NULL, "
                + "ProjectID INTEGER NOT NULL, "
                + "Status INTEGER NOT NULL)";

        String dropFieldTable = "DROP TABLE IF EXISTS Field";
        String createFieldTable = "CREATE TABLE Field ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "ProjectID INTEGER NOT NULL, "
                + "FieldPath TEXT NOT NULL, "
                + "KnownPath TEXT NOT NULL, "
                + "Width INTEGER NOT NULL, "
                + "XCoordinate INTEGER NOT NULL, "
                + "Title TEXT NOT NULL)";

        String dropProjectTable = "DROP TABLE IF EXISTS Project";
        String createProjectTable = "CREATE TABLE Project ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "Name TEXT NOT NULL, "
                + "RecordsPerBatch INTEGER NOT NULL, "
                + "FirstYCoord INTEGER, "
                + "RecordHeight INTEGER)";

        String dropRecordTable = "DROP TABLE IF EXISTS Record";
        String createRecordTable = "CREATE TABLE Record ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + "RowOnImage INTEGER NOT NULL, "
                + "BatchID INTEGER NOT NULL, "
                + "Data TEXT NOT NULL, "
                + "FieldID INTEGER NOT NULL)";

        String dropUserTable = "DROP TABLE IF EXISTS User";
        String createUserTable = "CREATE TABLE User ("
                + "ID INTEGER PRIMARY KEY NOT NULL UNIQUE, "
                + "Username TEXT NOT NULL UNIQUE, "
                + "Password TEXT NOT NULL, "
                + "FirstName TEXT NOT NULL, "
                + "LastName TEXT NOT NULL, "
                + "Email TEXT NOT NULL UNIQUE, "
                + "RecordCount INTEGER NOT NULL, "
                + "CurrentBatchID INTEGER NOT NULL)";
        try {
            initCurrTable(dropBatchTable, createBatchTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        try {
            initCurrTable(dropFieldTable, createFieldTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        try {
            initCurrTable(dropBatchTable, createBatchTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        try {
            initCurrTable(dropProjectTable, createProjectTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        try {
            initCurrTable(dropRecordTable, createRecordTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        try {
            initCurrTable(dropUserTable, createUserTable);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        }

        logger.exiting("server.database.Database", "initDBTables");
    }

    /**
     * Initializes the given table.
     *
     * @param dropStmt
     *            the SQL statement to drop the given table
     * @param createStmt
     *            the the SQL statement to create the given table
     */
    private void initCurrTable(String dropStmt, String createStmt)
            throws DatabaseException {
        logger.entering("test.server.database.Database", "initTable");

        PreparedStatement pstmt = null;
        try {
            //try {
                //startTransaction();
            //} catch (DatabaseException e) {
                //logger.log(Level.SEVERE, e.toString());
                //logger.log(Level.FINE, "STACKTRACE: ", e);
                //throw new DatabaseException(e.toString());
            //}

            pstmt = connection.prepareStatement(dropStmt);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement(createStmt);
            pstmt.executeUpdate();

            //try {
                //endTransaction(true);
            //} catch (DatabaseException e) {
                //logger.log(Level.SEVERE, e.toString());
                //logger.log(Level.FINE, "STACKTRACE: ", e);
                //throw new DatabaseException(e.toString());
            //}
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new DatabaseException(e.toString());
        } finally {
            closeSafely(pstmt);
        }

        logger.exiting("test.server.database.Database", "initTable");
    }
}

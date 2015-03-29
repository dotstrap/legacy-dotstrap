/**
 * Database.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 27, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.database;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.dao.*;

/**
 * The Class Database.
 */
public class Database {
  private static String DB_DIR            = "database";
  private static String DB_NAME           = "IndexerServer.sqlite";
  public static String  DB_FILE           = DB_DIR + File.separator + DB_NAME;
  private static String DB_CONNECTION_URL = "jdbc:sqlite:" + DB_DIR + File.separator + DB_NAME;

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  // DataBase Access //////////////////
  /** The database driver connection. */
  private Connection    connection;

  /**
   * The batch DataBaseAccess. interfaces with the database to modify the batch (image) table
   */
  private BatchDAO      batchDAO;
  /**
   * The field DataBaseAccess. interfaces with the database to modify the field table
   */
  private FieldDAO      fieldDAO;
  /**
   * The project DataBaseAccess. interfaces with the database to modify the project table
   */
  private ProjectDAO    projectDAO;
  /**
   * The record DataBaseAccess.
   *
   * interfaces with the database to modify the record table
   */
  private RecordDAO     recordDAO;
  /**
   * The user DataBaseAccess. interfaces with the database to modify the user table
   */
  private UserDAO       userDAO;

  //@formatter:off
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

  /**
   * Instantiates a new Database.
   *
   * @param bDao the b dao
   * @param fDao the f dao
   * @param pDao the dao
   * @param rDao the r dao
   */
  public Database(BatchDAO bDao, FieldDAO fDao, ProjectDAO pDao, RecordDAO rDao) {
    connection      = null;

    this.batchDAO   = bDao;
    this.fieldDAO   = fDao;
    this.projectDAO = pDao;
    this.recordDAO  = rDao;
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
  //@formatter:on
  /**
   * Initializes the Java SQL driver.
   *
   * @throws DatabaseException the database exception
   */
  public static void initDriver() throws DatabaseException {
    try {
      String driver = "org.sqlite.JDBC";
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    }
  }

  /**
   * Starts the transaction.
   *
   * @throws DatabaseException the database exception
   */
  public void startTransaction() throws DatabaseException {
    // Open a connection to the database and start a transaction
    try {
      assert (connection == null);
      connection = DriverManager.getConnection(DB_CONNECTION_URL);
      connection.setAutoCommit(false);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    }
  }

  /**
   * Ends the database transaction.
   *
   * @param shouldCommit - commit or rollback transaction?
   * @throws DatabaseException
   */
  public void endTransaction(boolean shouldCommit) {
    // Commit or rollback the transaction and finally close the connection
    if (connection != null) {
      try {
        if (shouldCommit) {
          connection.commit();
        } else {
          connection.rollback();
         }
         connection.close();
      } catch (SQLException e) {
        logger.log(Level.SEVERE, "STACKTRACE: ", e);
         //throw new DatabaseException(e.toString()); // FIXME: should this through a db exception?
      }
    }
  }

  /**
   * Initializes the database by sequentially dropping each table and then creating it.
   *
   * @throws DatabaseException the database exception
   */
  public void initTables() throws DatabaseException {
    try {
      batchDAO.initTable();
      fieldDAO.initTable();
      projectDAO.initTable();
      recordDAO.initTable();
      userDAO.initTable();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    }
  }
}

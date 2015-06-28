
package server.database;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.dao.*;


public class Database {
  private static String DB_DIR = "database";
  private static String DB_NAME = "IndexerServer.sqlite";
  public static String DB_FILE = DB_DIR + File.separator + DB_NAME;
  private static String DB_CONNECTION_URL = "jdbc:sqlite:" + DB_DIR + File.separator + DB_NAME;

  
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");
  }

  // DataBase Access //////////////////
  
  private Connection connection;

  
  private BatchDAO batchDAO;
  
  private FieldDAO fieldDAO;
  
  private ProjectDAO projectDAO;
  
  private RecordDAO recordDAO;
  
  private UserDAO userDAO;

  // @formatter:off
  
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

  
  public static void initDriver() throws DatabaseException {
    try {
      String driver = "org.sqlite.JDBC";
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new DatabaseException(e.toString());
    }
  }

  
  public void startTransaction() throws DatabaseException {
    //System.out.println("*****************STARTING TRANSACTION***********************");
    if (connection == null) {
      // Open a connection to the database and start a transaction
      try {
        assert (connection == null);
        connection = DriverManager.getConnection(DB_CONNECTION_URL);
        connection.setAutoCommit(false);
      } catch (Exception e) {
        logger.log(Level.SEVERE, "STACKTRACE: ", e);
        throw new DatabaseException(e.toString());
      }
    } else {
      logger.log(Level.SEVERE, "connection already open...");
    }
  }

  
  public void endTransaction(boolean shouldCommit) {
    //System.out.println("#####################ENDING TRANSACTION### SHOULDCOMMIT: " + shouldCommit);
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
        // throw new DatabaseException(e.toString()); // FIXME: should this throw a db exception?
      }
    }
  }

  
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

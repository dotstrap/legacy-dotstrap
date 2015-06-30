/**
 * Server.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.httphandler.indexerserverhandler.DownloadBatchHandler;
import server.httphandler.indexerserverhandler.DownloadFileHandler;
import server.httphandler.indexerserverhandler.GetFieldsHandler;
import server.httphandler.indexerserverhandler.GetProjectsHandler;
import server.httphandler.indexerserverhandler.GetSampleBatchHandler;
import server.httphandler.indexerserverhandler.SearchHandler;
import server.httphandler.indexerserverhandler.SubmitBatchHandler;
import server.httphandler.indexerserverhandler.ValidateUserHandler;

/**
 * The Class Server.
 */
public class Server {
  private static int MAX_WAITING_CONNECTIONS = 10;
  private static int DEFAULT_PORT = 39640;
  private static Logger logger;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    File logDir = new File("logs");
    if (!logDir.exists()) {
      try {
        logDir.mkdir();
      } catch (SecurityException e) {
        System.out.println("ERROR: unable to create log directory...");
      }
    }

    int portNum = DEFAULT_PORT;
    try {
      FileInputStream is = new FileInputStream("logging.properties");
      LogManager.getLogManager().readConfiguration(is);
      logger = Logger.getLogger("server");
    } catch (IOException e) {
      System.out.println("ERROR: unable to load logging properties file...");
    }
    logger.info("===============Initialized " + logger.getName() + " log...");

    if (args == null) {
      logger.info("No port number specified; using default port: "
          + DEFAULT_PORT + "....");
      portNum = DEFAULT_PORT;
    } else if (args.length == 1) {
      try {
        portNum = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        logger.log(Level.SEVERE, "Invalid port number format...");
        return;
      }
    } else {
      logger.log(Level.SEVERE, "Invalid port number argument...");
      return;
    }

    logger.info("Bootstrapping server...");
    try {
      new Server().bootstrap(portNum);
    } catch (ServerException e) {
      logger.log(Level.SEVERE, "STACKTRACE: ", e);
    }
  }

  // The server //@formatter:off
  private HttpServer server;
  // Handler objects
  private SearchHandler searchHandler;
  private GetFieldsHandler getFieldsHandler;
  private GetProjectsHandler getProjectsHandler;
  private GetSampleBatchHandler getSampleBatchHandler;
  private SubmitBatchHandler submitBatchHandler;
  private ValidateUserHandler validateUserHandler;
  private DownloadBatchHandler downloadBatchHandler;
  private DownloadFileHandler downloadFileHandler;

  /**
   * Instantiates a new server.
   */
  public Server() {
    logger.info("Initializing HTTP handlers...");
    searchHandler = new SearchHandler();
    validateUserHandler = new ValidateUserHandler();
    getFieldsHandler = new GetFieldsHandler();
    getProjectsHandler = new GetProjectsHandler();
    getSampleBatchHandler = new GetSampleBatchHandler();
    submitBatchHandler = new SubmitBatchHandler();
    downloadBatchHandler = new DownloadBatchHandler();
    downloadFileHandler = new DownloadFileHandler();
  } // @formatter:on

  /**
   * Bootstrap.
   *
   * @param portNum the port num
   * @throws ServerException the server exception
   */
  public void bootstrap(int portNum) throws ServerException {
    logger.info("Initializing Model...");
    try {
      ServerFacade.initialize();
    } catch (ServerException e) {
      throw new ServerException(e);
    }

    logger.info("Initializing HTTP server on port: " + portNum + "...");
    try {
      server = HttpServer.create(new InetSocketAddress(portNum),
          MAX_WAITING_CONNECTIONS);
    } catch (IOException e) {
      logger.log(Level.SEVERE,
          "Failed to initialize server on port: " + portNum + "...");
      throw new ServerException(e);
    }
    server.setExecutor(null); // use the default executor

    logger.info("Creating contexts...");
    server.createContext("/Search", searchHandler);
    server.createContext("/ValidateUser", validateUserHandler);
    server.createContext("/GetFields", getFieldsHandler);
    server.createContext("/GetProjects", getProjectsHandler);
    server.createContext("/GetSampleImage", getSampleBatchHandler);
    server.createContext("/SubmitBatch", submitBatchHandler);
    server.createContext("/DownloadBatch", downloadBatchHandler);
    server.createContext("/", downloadFileHandler);

    logger.info("Starting HTTP Server...");
    server.start();
  }

  /**
   * Stop.
   */
  public void stop() {
    server.stop(0);
  }
}

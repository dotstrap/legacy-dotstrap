package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.logging.*;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.httphandler.indexerserverhandler.*;

public class Server {
  private static int MAX_WAITING_CONNECTIONS = 10;

  private static int DEFAULT_PORT = 39640;

  private static Logger logger;

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
      logger.info("No port number specified; using default port: " + DEFAULT_PORT + "....");
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
  // Handler objects ////////////////////////////////
  private SearchHandler         searchHandler;
  private GetFieldsHandler      getFieldsHandler;
  private GetProjectsHandler    getProjectsHandler;
  private GetSampleBatchHandler getSampleBatchHandler;
  private SubmitBatchHandler    submitBatchHandler;
  private ValidateUserHandler   validateUserHandler;
  private DownloadBatchHandler  downloadBatchHandler;
  private DownloadFileHandler   downloadFileHandler;

  
  public Server() {
    logger.info("Initializing HTTP handlers...");
    searchHandler         = new SearchHandler();
    validateUserHandler   = new ValidateUserHandler();
    getFieldsHandler      = new GetFieldsHandler();
    getProjectsHandler    = new GetProjectsHandler();
    getSampleBatchHandler = new GetSampleBatchHandler();
    submitBatchHandler    = new SubmitBatchHandler();
    downloadBatchHandler  = new DownloadBatchHandler();
    downloadFileHandler   = new DownloadFileHandler();
  }  //@formatter:on

  public void bootstrap(int portNum) throws ServerException {
    logger.info("Initializing Model...");
    try {
      ServerFacade.initialize();
    } catch (ServerException e) {
      throw new ServerException(e);
    }

    logger.info("Initializing HTTP server on port: " + portNum + "...");
    try {
      server = HttpServer.create(new InetSocketAddress(portNum), MAX_WAITING_CONNECTIONS);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to initialize server on port: " + portNum + "...");
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

  public void stop() {
    server.stop(0);
  }
}

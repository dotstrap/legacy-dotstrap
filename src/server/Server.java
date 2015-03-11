/**
 * Server.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

import server.facade.ServerFacade;
import server.httphandler.*;

import com.sun.net.httpserver.HttpServer;

/**
 * The Class Server.
 * Initializes the server's logs (used in all classes
 * Creates HTTPHandler Objects
 * Bootstraps the HTTP Server
 */
public class Server {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        if (args == null) {
            SERVER_PORT_NUMBER = 50080;
        } else if (args.length > 0) {
            SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
        } else {
            SERVER_PORT_NUMBER = 8080;
        }
        new Server().bootstrap();
    }

    /**
     * Initializes the log.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void initLog() throws IOException {
        // FINER enables entering and exiting statements
        // SEVERE only enables error messages set to SEVERE
        Level logLevel = Level.FINER;

        logger = Logger.getLogger("server");
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("logs/server.log", false);
        fileHandler.setLevel(logLevel);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    /** The Constant MAX_WAITING_CONNECTIONS to the server. */
    private static final int      MAX_WAITING_CONNECTIONS = 10;

    /** Default port number the server runs on (can be overridden via CLI args. */
    private static int            SERVER_PORT_NUMBER      = 8080;

    /** The logger. */
    private static Logger         logger;

    static {
        try {
            initLog();
        } catch (IOException e) {
            System.out.println("Could not initialize log: " + e.getMessage());
        }
    }

    // The server
    private HttpServer            server;
    // Handler objects ////////////////////////////////
    private SearchHandler         searchHandler;
    private GetFieldsHandler      getFieldsHandler;
    private GetProjectsHandler    getProjectsHandler;
    private GetSampleImageHandler getSampleImageHandler;
    private SubmitBatchHandler    submitBatchHandler;
    private ValidateUserHandler   validateUserHandler;
    private DownloadBatchHandler  downloadBatchHandler;
    private DownloadFileHandler   downloadFileHandler;

    /**
     * Instantiates a new Server.
     *
     * @param searchHandler
     * @param getFieldsHandler
     * @param getProjectsHandler
     * @param getSampleImageHandler
     * @param submitBatchHandler
     * @param validateUserHandler
     * @param downloadBatchHandler
     * @param downloadFileHandler
     */
    public Server() {
        searchHandler = new SearchHandler();
        validateUserHandler = new ValidateUserHandler();
        getFieldsHandler = new GetFieldsHandler();
        getProjectsHandler = new GetProjectsHandler();
        getSampleImageHandler = new GetSampleImageHandler();
        submitBatchHandler = new SubmitBatchHandler();
        downloadBatchHandler = new DownloadBatchHandler();
        downloadFileHandler = new DownloadFileHandler();
    }

    /**
     * Bootstraps the server:
     * Initializes the models
     * Starts the HTTP server
     * Creates contexts
     * Starts the server
     */
    private void bootstrap() {

        logger.info("Initializing Model...");
        try {
            ServerFacade.initialize();
        } catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        logger.info("Initializing HTTP Server...");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(SERVER_PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        server.setExecutor(null); // use the default executor

        logger.info("Creating contexts...");
        server.createContext("/Search", searchHandler);
        server.createContext("/ValidateUser", validateUserHandler);
        server.createContext("/GetFields", getFieldsHandler);
        server.createContext("/GetProjects", getProjectsHandler);
        server.createContext("/GetSampleImage", getSampleImageHandler);
        server.createContext("/SubmitBatch", submitBatchHandler);
        server.createContext("/DownloadBatch", downloadBatchHandler);
        server.createContext("/Records", downloadFileHandler);

        logger.info("Starting HTTP Server...");
        server.start();
    }
}

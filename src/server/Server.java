/**
 * Server.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.httphandler.indexerserverhandler.*;

/**
 * The Class Server. Initializes the server's logs (used in all non-testing classes) Creates
 * HTTPHandler Objects Bootstraps the HTTP Server
 */
public class Server {

    /** The Constant MAX_WAITING_CONNECTIONS to the server. */
    private static final int    MAX_WAITING_CONNECTIONS = 10;

    /** Default port number the server runs on (can be overridden via CLI args. */
    private static int          DEFAULT_PORT            = 8080;

    /** The logger used throughout the project. */
    private static Logger       logger;
    private final static String LOG_NAME                = "server";

    /**
     * Entry point for the Indexer Server program
     *
     * @param args the port to run the indexer server on
     */
    public static void main(String[] args) {
        int portNum = 8080;
        try {
            final FileInputStream is = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
            logger = Logger.getLogger(LOG_NAME);
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("ERROR: unable to load logging properties file...");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }

        logger.info("Initialized " + LOG_NAME + " log...");

        if (args == null) {
            logger.info("No port number specified; using default port");
            portNum = DEFAULT_PORT;
        } else if (args.length == 1) {
            try {
                portNum = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.severe("Invalid port number argument...");
                return;
            }
        } else {
            logger.severe("Too many input arguments...");
            return;
        }

        logger.info("Bootstrapping server...");
        try {
            new Server().bootstrap(portNum);
        } catch (ServerException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
    }



    //@formatter:off
    // The server
    private HttpServer            server;
    // Handler objects ////////////////////////////////
    private SearchHandler         searchHandler;
    private GetFieldsHandler      getFieldsHandler;
    private GetProjectsHandler    getProjectsHandler;
    private GetSampleBatchHandler getSampleBatchHandler;
    private SubmitBatchHandler    submitBatchHandler;
    private ValidateUserHandler   validateUserHandler;
    private DownloadBatchHandler  downloadBatchHandler;
    private DownloadFileHandler   downloadFileHandler;

    /**
     * Instantiates a new Server. Server
     */
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
    }
    //@formatter:on

    /**
     * Bootstraps the server: Initializes the models Starts the HTTP server Creates contexts Starts
     * the server
     *
     * @param portNum
     */
    public void bootstrap(int portNum) throws ServerException {
        logger.info("Initializing Model...");
        try {
            ServerFacade.initialize();
        } catch (ServerException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

        logger.info("Initializing HTTP server on port: " + portNum + "...");
        try {
            server = HttpServer.create(new InetSocketAddress(portNum), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize server on port: " + portNum + "...");
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
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
        server.createContext("/Records", downloadFileHandler);
        // server.createContext("/", downloadFileHandler);

        logger.info("Starting HTTP Server...");
        server.start();
    }

    /**
     * Stops the server and frees the port
     */
    public void stop() {
        server.stop(0);
    }
}

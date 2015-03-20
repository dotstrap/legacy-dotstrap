/**
 * Server.java JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015. Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.logging.*;

import server.facade.ServerFacade;
// import server.facade.ServerFacade;
import server.httphandler.*;

import com.sun.net.httpserver.HttpServer;

/**
 * The Class Server. Initializes the server's logs (used in all non-testing classes) Creates
 * HTTPHandler Objects Bootstraps the HTTP Server
 */
public class Server {

    /** The Constant MAX_WAITING_CONNECTIONS to the server. */
    private static final int MAX_WAITING_CONNECTIONS = 10;

    /** Default port number the server runs on (can be overridden via CLI args. */
    private static int       SERVER_PORT_NUMBER      = 8080;

    private static Logger    logger;

    /**
     * The main method.
     *
     * @param args the port to run the indexer server on
     */
    public static void main(String[] args) {
        try {
            final FileInputStream is = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(is);
            logger = Logger.getLogger("server");
        } catch (final IOException e) {
            Logger.getAnonymousLogger().severe("ERROR: unable to load logging propeties file...");
            Logger.getAnonymousLogger().severe(e.getMessage());
        }

        if (args == null) {
            SERVER_PORT_NUMBER = 8080;
        } else if (args.length > 0) {
            SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
        } else {
            SERVER_PORT_NUMBER = 25565; // else use default minecraft server port number
        }

        logger.info("Bootstrapping server on port: " + SERVER_PORT_NUMBER + "...");
        try {
            new Server().bootstrap();
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
     */
    private void bootstrap() throws ServerException {
        logger.info("Initializing Model...");
        try {
            ServerFacade.initialize();
        } catch (ServerException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }

    //@formatter:off
        logger.info("Initializing HTTP Server...");
        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                                                             MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ServerException(e.toString());
        }
    //@formatter:on
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

        logger.info("Starting HTTP Server...");
        server.start();
    }
}

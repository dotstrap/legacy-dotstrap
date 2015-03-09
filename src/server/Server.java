/**
 * Server.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

import server.facade.ServerFacade;
import server.httphandler.*;

import com.sun.net.httpserver.HttpServer;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server {
    
    /** The logger. */
    private static Logger    logger;
    
    /** The Constant MAX_WAITING_CONNECTIONS. */
    private static final int MAX_WAITING_CONNECTIONS = 10;
    
    /** The server port number. */
    private static int       SERVER_PORT_NUMBER      = 8080;
    
    static {
        try {
            initLog();
        } catch (IOException e) {
            System.out.println("Could not initialize log: " + e.getMessage());
        }
    }
    
    private static void initLog() throws IOException {
        
        Level logLevel = Level.FINE;
        
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
        new Server().run();
    }
    
    // Handler objects
    /** The download batch handler. */
    private DownloadBatchHandler  downloadBatchHandler;
    
    /** The download file handler. */
    private DownloadFileHandler   downloadFileHandler;
    
    /** The get fields handler. */
    private GetFieldsHandler      getFieldsHandler;
    
    /** The get projects handler. */
    private GetProjectsHandler    getProjectsHandler;
    
    /** The get sample image handler. */
    private GetSampleImageHandler getSampleImageHandler;
    
    /** The search handler. */
    private SearchHandler         searchHandler;
    
    /** The server. */
    private HttpServer            server;
    
    /** The submit batch handler. */
    private SubmitBatchHandler    submitBatchHandler;
    
    /** The validate user handler. */
    private ValidateUserHandler   validateUserHandler;
    
    /**
     * Instantiates a new server.
     */
    public Server() {
        downloadBatchHandler = new DownloadBatchHandler();
        downloadFileHandler = new DownloadFileHandler();
        getFieldsHandler = new GetFieldsHandler();
        getProjectsHandler = new GetProjectsHandler();
        getSampleImageHandler = new GetSampleImageHandler();
        searchHandler = new SearchHandler();
        submitBatchHandler = new SubmitBatchHandler();
        validateUserHandler = new ValidateUserHandler();
        return;
    }
    
    /**
     * Run.
     */
    private void run() {
        
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
        
        // contexts
        server.createContext("/ValidateUser", validateUserHandler);
        server.createContext("/GetProjects", getProjectsHandler);
        server.createContext("/GetFields", getFieldsHandler);
        server.createContext("/GetSampleImage", getSampleImageHandler);
        server.createContext("/Search", searchHandler);
        server.createContext("/DownloadBatch", downloadBatchHandler);
        server.createContext("/SubmitBatch", submitBatchHandler);
        server.createContext("/Records", downloadFileHandler);
        
        logger.info("Starting HTTP Server...");
        
        server.start();
    }
}

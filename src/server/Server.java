package server;

import java.io.IOException;

import server.httphandler.DownloadBatchHandler;
import server.httphandler.DownloadFileHandler;
import server.httphandler.GetFieldsHandler;
import server.httphandler.GetProjectsHandler;
import server.httphandler.GetSampleImageHandler;
import server.httphandler.SearchHandler;
import server.httphandler.SubmitBatchHandler;
import server.httphandler.ValidateUserHandler;

import com.sun.net.httpserver.HttpServer;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server {

    /** The server port number. */
    private static int       SERVER_PORT_NUMBER;

    /** The Constant MAX_WAITING_CONNECTIONS. */
    private static final int MAX_WAITING_CONNECTIONS = 10;

    /**
     * Inits the log.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static void initLog() throws IOException {

    }

    /** The server. */
    private HttpServer server;

    /**
     * Instantiates a new server.
     */
    public Server() {
        return;
    }

    /**
     * Run.
     */
    private void run() {

        // contexts
        server.createContext("/ValidateUser", validateUserHandler);
        server.createContext("/GetProjects", getProjectsHandler);
        server.createContext("/GetFields", getFieldsHandler);
        server.createContext("/GetSampleImage", getSampleImageHandler);
        server.createContext("/Search", searchHandler);
        server.createContext("/DownloadBatch", downloadBatchHandler);
        server.createContext("/SubmitBatch", submitBatchHandler);
        server.createContext("/Records", downloadFileHandler);

        server.start();
    }

    // Handler objects
    /** The validate user handler. */
    private ValidateUserHandler   validateUserHandler   = new ValidateUserHandler();

    /** The get projects handler. */
    private GetProjectsHandler    getProjectsHandler    = new GetProjectsHandler();

    /** The get fields handler. */
    private GetFieldsHandler      getFieldsHandler      = new GetFieldsHandler();

    /** The get sample image handler. */
    private GetSampleImageHandler getSampleImageHandler = new GetSampleImageHandler();

    /** The search handler. */
    private SearchHandler         searchHandler         = new SearchHandler();

    /** The download batch handler. */
    private DownloadBatchHandler  downloadBatchHandler  = new DownloadBatchHandler();

    /** The submit batch handler. */
    private SubmitBatchHandler    submitBatchHandler    = new SubmitBatchHandler();

    /** The download file handler. */
    private DownloadFileHandler   downloadFileHandler   = new DownloadFileHandler();

    /**
     * The main method.
     *
     * @param args
     *            the arguments
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

}

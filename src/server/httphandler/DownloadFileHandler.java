package server.httphandler;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facade.ServerFacade;
import shared.communication.DownloadFileParameters;
import shared.communication.DownloadFileResult;

public class DownloadFileHandler implements HttpHandler {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    @Override
    public void handle(HttpExchange exchange) {
        logger.entering("server.HttpHandler.DownloadFileHandler", "handle");

        try {
            String url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
            DownloadFileResult result = null;
            DownloadFileParameters params = new DownloadFileParameters(url);
            result = ServerFacade.downloadFile(params);
            OutputStream response = exchange.getResponseBody();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            response.write(result.getFileBytes());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
    }
}

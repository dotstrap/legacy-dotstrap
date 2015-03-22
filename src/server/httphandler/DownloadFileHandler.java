/**
 * DownloadFileHandler.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facade.ServerFacade;

import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;

public class DownloadFileHandler implements HttpHandler {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("server.HttpHandler.DownloadFileHandler", "handle");

        try {
            String url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
            DownloadFileResponse result = null;
            DownloadFileRequest params = new DownloadFileRequest(url);
            result = ServerFacade.downloadFile(params);
            OutputStream response = exchange.getResponseBody();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            response.write(result.getFileBytes());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        } finally {
            exchange.getResponseBody().close();
        }
    }
}

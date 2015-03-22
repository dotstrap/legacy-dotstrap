/**
 * GetProjectsHandler.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.ServerException;
import server.facade.ServerFacade;

import shared.communication.GetProjectsParameters;
import shared.communication.GetProjectsResult;

public class GetProjectsHandler implements HttpHandler {

    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        XStream xs = new XStream(new DomDriver());
        GetProjectsParameters params =
                (GetProjectsParameters) xs.fromXML(exchange.getRequestBody());
        try {
            GetProjectsResult result = ServerFacade.getProjects(params);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            xs.toXML(result, exchange.getResponseBody());
        } catch (ServerException e) {
            // TODO output failed?
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
        } finally {
            exchange.getResponseBody().close();
        }
    }
}

/**
 * ValidateUserHandler.java
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

import shared.communication.*;
import server.facade.*;

public class ValidateUserHandler implements HttpHandler {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("server");
    }

    private XStream       xs = new XStream(new DomDriver());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ValidateUserParameters params =
                (ValidateUserParameters) xs.fromXML(exchange.getRequestBody());
        ValidateUserResult result = null;

        try {
            result = ServerFacade.validateUser(params);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
            return;
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xs.toXML(result, exchange.getResponseBody());
        exchange.getResponseBody().close();
    }
}

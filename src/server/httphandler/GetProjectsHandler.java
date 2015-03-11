/**
 * GetProjectsHandler.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.logging.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsHandler.
 */
public class GetProjectsHandler implements HttpHandler {

    /** The logger. */
    private static Logger logger = Logger.getLogger("indexer-server");

    /**
     * (non-Javadoc).
     * the arg0
     *
     * @param exchange the exchange
     * @throws IOException Signals that an I/O exception has occurred.
     * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub

    }

}

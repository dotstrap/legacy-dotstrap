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

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        XStream xs = new XStream(new DomDriver());
        ValidateUserParameters params = (ValidateUserParameters) xs
                .fromXML(exchange.getRequestBody());

        try {
            ValidateUserResult result = ServerFacade.validateUser(params);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            xs.toXML(result, exchange.getResponseBody());
            exchange.getResponseBody().close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    -1);
            exchange.getResponseBody().close();
        }
    }
}

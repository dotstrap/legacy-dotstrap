/**
q * IndexerServerHandler.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import shared.InvalidCredentialsException;
import shared.communication.*;

/**
 * Template class for the HTTP handlers for the various operations.
 *
 * This class uses its derived classes' doOperation() methods to handle the specifics of each type
 * of operation.
 */
public abstract class IndexerServerHandler implements HttpHandler {
  protected static Logger logger = Logger.getLogger(ServerFacade.LOG_NAME); // @formatter:off

  protected static final String SERVER = HttpServer.class.getName() + " ("
      + System.getProperty("os.name") + ")";
  protected static final String CONTENT_TYPE = "text/xml";

  protected XStream xStream = new XStream(new DomDriver());

  private Request  request;
  private Response response;

  private String   urlPrefix; // @formatter:on

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    final Headers headers = exchange.getResponseHeaders();
    headers.add("Server", SERVER);
    headers.add("Content-Type", CONTENT_TYPE);

    urlPrefix = String.format("http://%s:%d/",// @formatter:off
        exchange.getLocalAddress().getHostString(),
        exchange.getLocalAddress().getPort()); // @formatter:on

    int statusCode;
    try {
      request = (Request) xStream.fromXML(exchange.getRequestBody());
      statusCode = doRequest();

      exchange.sendResponseHeaders(statusCode, 0);
      xStream.toXML(response, exchange.getResponseBody());

    } catch (InvalidCredentialsException e) {
      statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
      exchange.sendResponseHeaders(statusCode, -1);
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);

    } catch (XStreamException e) {
      statusCode = HttpURLConnection.HTTP_BAD_REQUEST;
      exchange.sendResponseHeaders(statusCode, -1);
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);

    } catch (Exception e) {
      statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
      exchange.sendResponseHeaders(statusCode, -1);
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);

    } finally {
      exchange.close();
    }
    logger.info("HTTP status code: " + statusCode + " @ " + this.getClass().getSimpleName());
  }

  /**
   * Processes the request and returns a response and status code.
   *
   * @param request The request object received in the HTTP request
   * @param response The response object to return
   * @return The HTTP status code to return
   * @throws DatabaseException
   * @throws IOException
   */
  protected abstract int doRequest() throws ServerException, DatabaseException,
      InvalidCredentialsException;

  /**
   * Validates user credentials.
   *
   * @param user The user credentials to validate
   * @return True if credentials are valid, false otherwise
   * @throws InvalidCredentialsException if the credentials are invalid
   */
  public static boolean authenticate(String username, String password) {
    final ValidateUserRequest auth = new ValidateUserRequest();
    auth.setUsername(username);
    auth.setPassword(password);
    boolean isValid = true; // FIXME: this should default to false...
    try {
      ServerFacade.validateUser(auth);
    } catch (final InvalidCredentialsException e) {
      logger.log(Level.SEVERE,
          String.format("ERROR: username: %s & password: %s are invalid", username, password));
      logger.log(Level.FINE, "STACKTRACE: ", e);
      isValid = false;
    }
    return isValid;
  }

  protected Request getRequest() {
    return request;
  }

  protected void setResponse(Response response) {
    this.response = response;
  }
}

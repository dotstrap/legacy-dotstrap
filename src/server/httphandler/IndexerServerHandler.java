/**
 * IndexerServerHandler.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
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

import shared.communication.*;

/**
 * Template class for the HTTP handlers for the various operations.
 *
 * This class uses its derived classes' doOperation() methods to handle the specifics of each type
 * of operation.
 */
public abstract class IndexerServerHandler implements HttpHandler {

  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("server");

  } // @formatter:off

  /** The server. */
  protected static String SERVER = HttpServer.class.getName() + " ("
      + System.getProperty("os.name") + ")";

  /** The content type. */
  protected static String CONTENT_TYPE = "text/xml";

  /** The x stream. */
  protected XStream xStream = new XStream(new DomDriver());

  private Request  request;
  private Response response;

  private String   urlPrefix; // @formatter:on

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Headers headers = exchange.getResponseHeaders();
    headers.add("Server", SERVER);
    headers.add("Content-Type", CONTENT_TYPE);

    urlPrefix = String.format("http://%s:%d/",// @formatter:off
        exchange.getLocalAddress().getHostString(),
        exchange.getLocalAddress().getPort()); // @formatter:on

    int statusCode;
    try {
      request = (Request) xStream.fromXML(exchange.getRequestBody());
      statusCode = doRequest();

      int responseLength = -1; // FIXME: validate user no longer returns HTTP_UNAUTHORIZED
      if (statusCode == HttpURLConnection.HTTP_OK) {
        responseLength = 0;
      }
      exchange.sendResponseHeaders(statusCode, responseLength);

      xStream.toXML(response, exchange.getResponseBody());

    } catch (XStreamException e) {
      statusCode = HttpURLConnection.HTTP_BAD_REQUEST;
      exchange.sendResponseHeaders(statusCode, -1);
      logger.log(Level.SEVERE, "STACKTRACE: ", e);

    } catch (Exception e) {
      statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
      exchange.sendResponseHeaders(statusCode, -1);
      logger.log(Level.SEVERE, "STACKTRACE: ", e);

    } finally {
      exchange.close();
    }
    logger.info("HTTP status code: " + statusCode + " @ " + this.getClass().getSimpleName());
  }

  /**
   * Processes the request and returns a response and status code.
   *
   * @return The HTTP status code to return
   * @throws ServerException the server exception
   * @throws DatabaseException the database exception
   */
  protected abstract int doRequest() throws ServerException, DatabaseException;

  protected Request getRequest() {
    return request;
  }

  protected void setResponse(Response response) {
    this.response = response;
  }

  /**
   * Validates user credentials.
   *
   * @param username the username
   * @param password the password
   * @return True if credentials are valid, false otherwise
   */
  public static boolean authenticate(String username, String password) throws DatabaseException,
      ServerException {
    ValidateUserRequest auth = new ValidateUserRequest();
    auth.setUsername(username);
    auth.setPassword(password);
    // ServerFacade.validateUser(auth);
    // logger.warning("INVALID: username: " + username + " & password: " + password + "...");

    return (ServerFacade.validateUser(auth) != null);
  }
}

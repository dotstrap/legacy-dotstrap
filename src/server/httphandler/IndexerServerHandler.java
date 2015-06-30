/**
 * IndexerServerHandler.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server.httphandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import shared.communication.Request;
import shared.communication.Response;
import shared.communication.ValidateUserRequest;

/**
 * The Class IndexerServerHandler.
 */
public abstract class IndexerServerHandler implements HttpHandler {
  private static Logger logger;

  static {
    logger = Logger.getLogger("server");
  }

  protected static String SERVER =
      HttpServer.class.getName() + " (" + System.getProperty("os.name") + ")";
  protected static String CONTENT_TYPE = "text/xml";

  /**
   * Authenticate.
   *
   * @param username the username
   * @param password the password
   * @return true, if successful
   * @throws DatabaseException the database exception
   * @throws ServerException the server exception
   */
  public static boolean authenticate(String username, String password)// @formatter:off
      throws DatabaseException, ServerException { // @formatter:on
    ValidateUserRequest auth = new ValidateUserRequest();
    auth.setUsername(username);
    auth.setPassword(password);

    return (ServerFacade.validateUser(auth).getUser() != null);
  }

  protected XStream xStream = new XStream(new DomDriver());
  private Request request;
  private Response response;
  private String urlPrefix;

  public String getUrlPrefix() {
    return urlPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Headers headers = exchange.getResponseHeaders();
    headers.add("Server", SERVER);
    headers.add("Content-Type", CONTENT_TYPE);

    setUrlPrefix(String.format("http://%s:%d/", // @formatter:off
        exchange.getLocalAddress().getHostString(),
        exchange.getLocalAddress().getPort())); // @formatter:on

    int statusCode;
    try {
      request = (Request) xStream.fromXML(exchange.getRequestBody());
      statusCode = doRequest();

      // FIXME: validate user no longer returns HTTP_UNAUTHORIZED
      int responseLength = -1;
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
    logger.fine("HTTP status code: " + statusCode + " @ "
        + this.getClass().getSimpleName());
  }

  public void setUrlPrefix(String urlPrefix) {
    this.urlPrefix = urlPrefix;
  }

  /**
   * Do request.
   *
   * @return the int
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
}

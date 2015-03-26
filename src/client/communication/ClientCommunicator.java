/**
 * ClientCommunicator.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.ServerException;

import shared.communication.*;
import shared.model.Record;

/**
 * The Class ClientCommunicator.
 */
public class ClientCommunicator {
  /** The logger used throughout the project. */
  private static Logger logger;
  static {
    logger = Logger.getLogger("client");
  }

  private XStream       xs = new XStream(new DomDriver());

  private String        URL_PREFIX;
  private String        host;
  private int           port;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Instantiates a new ClientCommunicator.
   *
   */
  public ClientCommunicator() {
    host = "localhost";
    port = 50080;
    URL_PREFIX = String.format("http://%s:%d", host, port);
  }

  /**
   * Instantiates a new client communicator.
   *
   * @param port the port
   * @param host the host
   */
  public ClientCommunicator(String port, String host) {
    this.port = Integer.parseInt(port);
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
  }

  /**
   * Validate user.
   *
   * @param params the params
   * @return the validate user response
   */
  public ValidateUserResponse validateUser(ValidateUserRequest params) throws ServerException {
    ValidateUserResponse result = (ValidateUserResponse) doPost("/ValidateUser", params);
    return result;
  }

  /**
   * Gets the projects.
   *
   * @param creds the creds
   * @return the projects
   * @throws ServerException the client exception
   */
  public GetProjectsResponse getProjects(GetProjectsRequest creds) throws ServerException {
    GetProjectsResponse result = null;
    try {
      result = (GetProjectsResponse) doPost("/GetProjects", creds);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Gets the sample batch.
   *
   * @param params the params
   * @return the sample batch
   * @throws ServerException the client exception
   */
  public GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest params)
      throws ServerException {
    GetSampleBatchResponse result = null;
    try {
      result = (GetSampleBatchResponse) doPost("/GetSampleImage", params);
      URL url = new URL(URL_PREFIX);
      result.setUrlPrefix(url);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Download batch.
   *
   * @param params the params
   * @return the download batch response
   * @throws ServerException the client exception
   */
  public DownloadBatchResponse downloadBatch(DownloadBatchRequest params) throws ServerException {
    DownloadBatchResponse result = null;
    try {
      result = (DownloadBatchResponse) doPost("/DownloadBatch", params);
      URL url = new URL(URL_PREFIX);
      result.setUrlPrefix(url);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Submit batch.
   *
   * @param params the params
   * @return the submit batch response
   * @throws ServerException the client exception
   */
  public SubmitBatchResponse submitBatch(SubmitBatchRequest params) throws ServerException {
    SubmitBatchResponse result = null;
    try {
      result = (SubmitBatchResponse) doPost("/SubmitBatch", params);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Gets the fields.
   *
   * @param params the params
   * @return the fields
   * @throws ServerException the client exception
   */
  public GetFieldsResponse getFields(GetFieldsRequest params) throws ServerException {
    GetFieldsResponse result = null;
    try {
      result = (GetFieldsResponse) doPost("/GetFields", params);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Search.
   *
   * @param params the params
   * @return the search response
   * @throws ServerException the client exception
   */
  public SearchResponse search(SearchRequest params) throws ServerException {
    SearchResponse result;
    try {
      result = (SearchResponse) doPost("/Search", params);
      List<URL> urls = new ArrayList<URL>();
      for (Record r : result.getFoundRecords()) {
        URL url = new URL(URL_PREFIX + "/" + r.getBatchURL());
        urls.add(url);
      }
      result.setUrls(urls);
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Download file.
   *
   * @param params the params
   * @return the download file response
   * @throws ServerException the client exception
   */
  public DownloadFileResponse downloadFile(DownloadFileRequest params) throws ServerException {
    return new DownloadFileResponse(doGet(URL_PREFIX + File.separator + params.getUrl()));
  }

  /**
   * Do get.
   *
   * @param urlPath the url path
   * @return the byte[]
   * @throws ServerException the client exception
   */
  public byte[] doGet(String urlPath) throws ServerException {
    byte[] result = null;
    try {
      URL url = new URL(urlPath);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setDoOutput(true);
      connection.connect();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream response = connection.getInputStream();
        result = IOUtils.toByteArray(response);
        response.close();
      }
    } catch (Exception e) {
      // logger.log(Level.SEVERE, "STACKTRACE: ", e);
      throw new ServerException(e);
    }
    return result;
  }

  private Response doPost(String postCommand, Request request) throws ServerException {
    assert postCommand != null;
    assert request != null;
    try {
      URL url = new URL(URL_PREFIX + postCommand);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestProperty("Accept", "html/text");

      connection.connect();

      OutputStream requestBody = connection.getOutputStream();
      xs.toXML(request, requestBody);
      requestBody.close();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        Response r = (Response) xs.fromXML(connection.getInputStream());
        connection.getInputStream().close();
        return r;
      } else {
        connection.getInputStream().close();
        throw new ServerException(String.format("POST FAILED: %s HTTP code: %d", postCommand,
            connection.getResponseCode()));
      }
    } catch (Exception e) {
      throw new ServerException(e);
    }
  }
}

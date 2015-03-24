/**
 * ClientCommunicator.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.ClientException;

import shared.InvalidCredentialsException;
import shared.communication.*;
import shared.model.Record;

public class ClientCommunicator {
  /** The logger used throughout the project. */
  private static Logger logger; // @formatter:off
  static {
    logger = Logger.getLogger("client");
  }

  private final  XStream xs = new XStream(new DomDriver());

  private String URL_PREFIX;
  private String host;
  private int    port; // @formatter:on

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

  public ClientCommunicator(String port, String host) {
    this.port = Integer.parseInt(port);
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
  }

  public ValidateUserResponse validateUser(ValidateUserRequest params)
      throws InvalidCredentialsException {
    ValidateUserResponse result = null;
    try {
      result = (ValidateUserResponse) doPost("/ValidateUser", params);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new InvalidCredentialsException(e);
    }
    return result;
  }

  public GetProjectsResponse getProjects(GetProjectsRequest creds) throws ClientException {
    GetProjectsResponse result = null;
    try {
      result = (GetProjectsResponse) doPost("/GetProjects", creds);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest params) throws ClientException {
    GetSampleBatchResponse result = null;
    try {
      result = (GetSampleBatchResponse) doPost("/GetSampleImage", params);
      final URL url = new URL(URL_PREFIX);
      result.setUrlPrefix(url);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public DownloadBatchResponse downloadBatch(DownloadBatchRequest params) throws ClientException {
    DownloadBatchResponse result = null;
    try {
      result = (DownloadBatchResponse) doPost("/DownloadBatch", params);
      final URL url = new URL(URL_PREFIX);
      result.setUrlPrefix(url);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public SubmitBatchResponse submitBatch(SubmitBatchRequest params) throws ClientException {
    SubmitBatchResponse result = null;
    try {
      result = (SubmitBatchResponse) doPost("/SubmitBatch", params);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public GetFieldsResponse getFields(GetFieldsRequest params) throws ClientException {
    GetFieldsResponse result = null;
    try {
      result = (GetFieldsResponse) doPost("/GetFields", params);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public SearchResponse search(SearchRequest params) throws ClientException {
    SearchResponse result;
    try {
      result = (SearchResponse) doPost("/Search", params);
      final List<URL> urls = new ArrayList<URL>();
      for (final Record r : result.getFoundRecords()) {
        final URL url = new URL(URL_PREFIX + "/" + r.getBatchURL());
        urls.add(url);
      }
      result.setUrls(urls);
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  public DownloadFileResponse downloadFile(DownloadFileRequest params) throws ClientException {
    return new DownloadFileResponse(doGet(URL_PREFIX + File.separator + params.getUrl()));
  }

  public byte[] doGet(String urlPath) throws ClientException {
    byte[] result = null;
    try {
      final URL url = new URL(urlPath);
      final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setDoOutput(true);
      connection.connect();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        final InputStream response = connection.getInputStream();
        result = IOUtils.toByteArray(response);
        response.close();
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
    return result;
  }

  private Response doPost(String postCommand, Request request) throws ClientException {
    assert postCommand != null;
    assert request != null;
    OutputStream requestBody = null;
    URL url;
    try {
      url = new URL(URL_PREFIX + postCommand);
      final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestProperty("Accept", "html/text");

      connection.connect();
      requestBody = connection.getOutputStream();
      xs.toXML(request, requestBody);
      requestBody.close();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        final Response r = (Response) xs.fromXML(connection.getInputStream());
        connection.getInputStream().close();
        return r;
      } else {
        connection.getInputStream().close();
        throw new ClientException(String.format("POST FAILED: %s HTTP code: %d", postCommand,
            connection.getResponseCode()));
      }
    } catch (final Exception e) {
      logger.log(Level.SEVERE, e.toString());
      logger.log(Level.FINE, "STACKTRACE: ", e);
      throw new ClientException(e);
    }
  }
}

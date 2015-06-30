/**
 * ClientCommunicator.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.util.ClientLogManager;

import server.ServerException;

import shared.communication.DownloadBatchRequest;
import shared.communication.DownloadBatchResponse;
import shared.communication.DownloadFileRequest;
import shared.communication.DownloadFileResponse;
import shared.communication.GetFieldsRequest;
import shared.communication.GetFieldsResponse;
import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.communication.Request;
import shared.communication.Response;
import shared.communication.SearchRequest;
import shared.communication.SearchResponse;
import shared.communication.SubmitBatchRequest;
import shared.communication.SubmitBatchResponse;
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;

/**
 * The Class ClientCommunicator.
 */
public class ClientCommunicator {
  private XStream xs = new XStream(new DomDriver());
  private String URL_PREFIX;
  private String host;
  private int port;

  /**
   * Instantiates a new client communicator.
   */
  public ClientCommunicator() {
    host = "localhost";
    port = 39640;
    URL_PREFIX = String.format("http://%s:%d", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized default: " + host + ":" + port);
  }

  /**
   * Instantiates a new client communicator.
   *
   * @param host the host
   * @param port the port
   */
  public ClientCommunicator(String host, int port) {
    this.port = port;
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized " + host + ":" + port);
  }

  /**
   * Instantiates a new client communicator.
   *
   * @param host the host
   * @param port the port
   */
  public ClientCommunicator(String host, String port) {
    this.port = Integer.parseInt(port);
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized " + host + ":" + port);
  }

  /**
   * Do get.
   *
   * @param urlPath the url path
   * @return the byte[]
   * @throws ServerException the server exception
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
      throw new ServerException(e);
    }
    return result;
  }

  /**
   * Download batch.
   *
   * @param params the params
   * @return the download batch response
   * @throws ServerException the server exception
   * @throws MalformedURLException the malformed url exception
   */
  public DownloadBatchResponse downloadBatch(DownloadBatchRequest params)
      throws ServerException, MalformedURLException {
    DownloadBatchResponse result =
        (DownloadBatchResponse) doPost("/DownloadBatch", params);
    result.setUrlPrefix(new URL(URL_PREFIX));
    String url = URL_PREFIX + File.separator + result.getBatch().getFilePath();
    ClientLogManager.getLogger().log(Level.FINER, url);
    return result;
  }

  /**
   * Download file.
   *
   * @param params the params
   * @return the download file response
   * @throws ServerException the server exception
   */
  public DownloadFileResponse downloadFile(DownloadFileRequest params)
      throws ServerException {
    String url = URL_PREFIX + File.separator + params.getUrl();
    ClientLogManager.getLogger().log(Level.FINER, url);
    return new DownloadFileResponse(doGet(url));
  }

  /**
   * Gets the fields.
   *
   * @param params the params
   * @return the fields
   * @throws ServerException the server exception
   */
  public GetFieldsResponse getFields(GetFieldsRequest params)
      throws ServerException {
    return (GetFieldsResponse) doPost("/GetFields", params);
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  /**
   * Gets the projects.
   *
   * @param params the params
   * @return the projects
   * @throws ServerException the server exception
   */
  public GetProjectsResponse getProjects(GetProjectsRequest params)
      throws ServerException {
    return (GetProjectsResponse) doPost("/GetProjects", params);
  }

  /**
   * Gets the sample batch.
   *
   * @param params the params
   * @return the sample batch
   * @throws ServerException the server exception
   * @throws MalformedURLException the malformed url exception
   */
  public GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest params)
      throws ServerException, MalformedURLException {
    GetSampleBatchResponse result =
        (GetSampleBatchResponse) doPost("/GetSampleImage", params);
    result.setUrlPrefix(new URL(URL_PREFIX));

    String url =
        URL_PREFIX + File.separator + result.getSampleBatch().getFilePath();
    ClientLogManager.getLogger().log(Level.FINER, url);
    return result;
  }

  /**
   * Search.
   *
   * @param params the params
   * @return the search response
   * @throws ServerException the server exception
   * @throws MalformedURLException the malformed url exception
   */
  public SearchResponse search(SearchRequest params)
      throws ServerException, MalformedURLException {
    SearchResponse result = (SearchResponse) doPost("/Search", params);
    URL url = new URL(URL_PREFIX);
    result.setUrlPrefix(url);
    return result;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Submit batch.
   *
   * @param params the params
   * @return the submit batch response
   * @throws ServerException the server exception
   */
  public SubmitBatchResponse submitBatch(SubmitBatchRequest params)
      throws ServerException {
    return (SubmitBatchResponse) doPost("/SubmitBatch", params);
  }

  /**
   * Validate user.
   *
   * @param params the params
   * @return the validate user response
   * @throws ServerException the server exception
   */
  public ValidateUserResponse validateUser(ValidateUserRequest params)
      throws ServerException {
    return (ValidateUserResponse) doPost("/ValidateUser", params);
  }

  /**
   * Do post.
   *
   * @param postCommand the post command
   * @param request the request
   * @return the response
   * @throws ServerException the server exception
   */
  private Response doPost(String postCommand, Request request)
      throws ServerException {
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
        throw new ServerException(String.format("POST FAILED: %s HTTP code: %d",
            postCommand, connection.getResponseCode()));
      }
    } catch (Exception e) {
      throw new ServerException(e);
    }
  }
}

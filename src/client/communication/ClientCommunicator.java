/*
 * 
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

public class ClientCommunicator {
  private XStream xs = new XStream(new DomDriver());

  private String URL_PREFIX;
  private String host;
  private int port;

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

  public ClientCommunicator() {
    host = "localhost";
    port = 39640;
    URL_PREFIX = String.format("http://%s:%d", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized default: " + host + ":" + port);
  }

  public ClientCommunicator(String host, String port) {
    this.port = Integer.parseInt(port);
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized " + host + ":" + port);
  }

  public ClientCommunicator(String host, int port) {
    this.port = port;
    this.host = host;
    URL_PREFIX = String.format("http://%s:%s", host, port);
    ClientLogManager.getLogger().log(Level.FINER,
        "Initialized " + host + ":" + port);
  }

  public ValidateUserResponse validateUser(ValidateUserRequest params)
      throws ServerException {
    return (ValidateUserResponse) doPost("/ValidateUser", params);
  }

  public GetProjectsResponse getProjects(GetProjectsRequest params)
      throws ServerException {
    return (GetProjectsResponse) doPost("/GetProjects", params);
  }

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

  public DownloadBatchResponse downloadBatch(DownloadBatchRequest params)
      throws ServerException, MalformedURLException {
    DownloadBatchResponse result =
        (DownloadBatchResponse) doPost("/DownloadBatch", params);
    result.setUrlPrefix(new URL(URL_PREFIX));
    String url = URL_PREFIX + File.separator + result.getBatch().getFilePath();
    ClientLogManager.getLogger().log(Level.FINER, url);
    return result;
  }

  public SubmitBatchResponse submitBatch(SubmitBatchRequest params)
      throws ServerException {
    return (SubmitBatchResponse) doPost("/SubmitBatch", params);
  }

  public GetFieldsResponse getFields(GetFieldsRequest params)
      throws ServerException {
    return (GetFieldsResponse) doPost("/GetFields", params);
  }

  public SearchResponse search(SearchRequest params)
      throws ServerException, MalformedURLException {
    SearchResponse result = (SearchResponse) doPost("/Search", params);
    URL url = new URL(URL_PREFIX);
    result.setUrlPrefix(url);
    return result;
  }

  public DownloadFileResponse downloadFile(DownloadFileRequest params)
      throws ServerException {
    String url = URL_PREFIX + File.separator + params.getUrl();
    ClientLogManager.getLogger().log(Level.FINER, url);
    return new DownloadFileResponse(doGet(url));
  }

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

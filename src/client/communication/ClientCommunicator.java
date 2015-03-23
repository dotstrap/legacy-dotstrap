/**
 * ClientCommunicator.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.ClientException;

import shared.communication.*;
import shared.model.Record;

public class ClientCommunicator {

    /** The logger used throughout the project. */
    private static Logger       logger;

    //@formatter:off
    private String       SERVER_HOST = "localhost";
    private int          SERVER_PORT = 50080;
    private String       URL_PREFIX  = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    private final String HTTP_GET    = "GET";
    private final String HTTP_POST   = "POST";
    //@formatter:on
    public String getHost() {
        return SERVER_HOST;
    }

    public void setHost(String host) {
        this.SERVER_HOST = host;
    }

    public int getPort() {
        return SERVER_PORT;
    }

    public void setPort(int port) {
        this.SERVER_PORT = port;
    }

    /**
     * Instantiates a new ClientCommunicator.
     *
     */
    public ClientCommunicator() {
        logger = Logger.getLogger("client");
    }

    public ClientCommunicator(String port, String host) {
        logger = Logger.getLogger("client");
        this.SERVER_PORT = Integer.parseInt(port);
        this.SERVER_HOST = host;
        this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }

    public ValidateUserResponse validateUser(ValidateUserRequest params) {
        ValidateUserResponse result = null;
        try {
            result = (ValidateUserResponse) doPost("/ValidateUser", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetProjectsResponse getProjects(GetProjectsRequest creds) {
        GetProjectsResponse result = null;
        try {
            result = (GetProjectsResponse) doPost("/GetProjects", creds);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetSampleBatchResponse getSampleBatch(GetSampleBatchRequest params)
                    throws ClientException {
        GetSampleBatchResponse result = null;
        try {
            result = (GetSampleBatchResponse) doPost("/GetSampleImage", params);
            URL url = new URL(URL_PREFIX + "/" + result.getSampleBatch().getFilePath());
            result.setUrl(url);
        } catch (MalformedURLException e) {
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
            URL url = new URL(URL_PREFIX + "/" + result.getBatch().getFilePath());
            result.setUrl(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
        return result;
    }

    public SubmitBatchResponse submitBatch(SubmitBatchRequest params) {
        SubmitBatchResponse result = null;
        try {
            result = (SubmitBatchResponse) doPost("/SubmitBatch", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetFieldsResponse getFields(GetFieldsRequest params) {
        GetFieldsResponse result = null;
        try {
            result = (GetFieldsResponse) doPost("/GetFields", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public SearchResponse search(SearchRequest params) throws ClientException {
        SearchResponse result;
        try {
            result = (SearchResponse) doPost("/Search", params);
            List<URL> urls = new ArrayList<URL>();
            for (Record r : result.getFoundRecords()) {
                URL url = new URL(URL_PREFIX + "/" + r.getBatchURL());
                urls.add(url);
            }
            result.setUrls(urls);
        } catch (ClientException | MalformedURLException e) {
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
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.setDoOutput(true);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream response = connection.getInputStream();
                result = IOUtils.toByteArray(response);
                response.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
        return result;
    }

    public Object doPost(String commandName, Object postData) throws ClientException {
        assert commandName != null;
        assert postData != null;

        URL url;
        try {
            url = new URL(URL_PREFIX + commandName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "html/text");
            connection.connect();
            XStream x = new XStream(new DomDriver());
            x.toXML(postData, connection.getOutputStream());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Object o = (Object) x.fromXML(connection.getInputStream());
                return o;
            } else {
                // return null;
                throw new ClientException(String.format("doPost FAILED: %s HTTP code: %d",
                                commandName, connection.getResponseCode()));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
    }

}

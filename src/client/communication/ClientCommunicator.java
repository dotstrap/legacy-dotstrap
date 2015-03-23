/**
 * ClientCommunicator.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import java.io.File;
import java.io.InputStream;
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

import shared.communication.*;
import shared.model.Record;

public class ClientCommunicator {
    /** The logger used throughout the project. */
    private static Logger logger;
    static {
        logger = Logger.getLogger("client");
    }

    private String        host       = "localhost";
    private int           port       = 50080;
    private String        URL_PREFIX = "http://" + host + ":" + port;

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
      host       = "localhost";
       port       = 50080;
        this.URL_PREFIX = String.format("http://%s:%d", host, port);
    }

    public ClientCommunicator(String port, String host) {
        this.port = Integer.parseInt(port);
        this.host = host;
        this.URL_PREFIX = String.format("http://%s:%d", host, port);
    }

    public ValidateUserResponse validateUser(ValidateUserRequest params) {
        ValidateUserResponse result = null;
        try {
            result = (ValidateUserResponse) doPost("/ValidateUser", params);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetProjectsResponse getProjects(GetProjectsRequest creds) {
        GetProjectsResponse result = null;
        try {
            result = (GetProjectsResponse) doPost("/GetProjects", creds);
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetFieldsResponse getFields(GetFieldsRequest params) {
        GetFieldsResponse result = null;
        try {
            result = (GetFieldsResponse) doPost("/GetFields", params);
        } catch (Exception e) {
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
        } catch (Exception e) {
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
            connection.setRequestMethod("GET");
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

    public Object doPost(String postCommand, Object postData) throws ClientException {
        assert postCommand != null;
        assert postData != null;

        URL url;
        try {
            url = new URL(URL_PREFIX + postCommand);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "html/text");
            connection.connect();
            XStream xs = new XStream(new DomDriver());
            xs.toXML(postData, connection.getOutputStream());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Object o = (Object) xs.fromXML(connection.getInputStream());
                return o;
            } else {
                // return null;
                throw new ClientException(String.format("POST FAILED: %s HTTP code: %d",
                                postCommand, connection.getResponseCode()));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
    }

}

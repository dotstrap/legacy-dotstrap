package client.communication;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import shared.communication.*;
import shared.model.Record;

import client.ClientException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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

    public ValidateUserResult validateUser(ValidateUserParameters params) {
        ValidateUserResult result = null;
        try {
            result = (ValidateUserResult) doPost("/ValidateUser", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetProjectsResult getProjects(GetProjectsParameters creds) {
        GetProjectsResult result = null;
        try {
            result = (GetProjectsResult) doPost("/GetProjects", creds);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetSampleBatchResult getSampleBatch(GetSampleBatchParameters params)
                    throws ClientException {
                    GetSampleBatchResult result = null;
        try {
            result = (GetSampleBatchResult) doPost("/GetSampleImage", params);
            URL url = new URL(URL_PREFIX + "/" + result.getSampleBatch().getFilePath());
            result.setUrl(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
        return result;
    }

    public DownloadBatchResult downloadBatch(DownloadBatchParameters params) throws ClientException {
        DownloadBatchResult result = null;
        try {
            result = (DownloadBatchResult) doPost("/DownloadBatch", params);
            URL url = new URL(URL_PREFIX + "/" + result.getBatch().getFilePath());
            result.setUrl(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
            throw new ClientException(e);
        }
        return result;
    }

    public SubmitBatchResult submitBatch(SubmitBatchParameters params) {
        SubmitBatchResult result = null;
        try {
            result = (SubmitBatchResult) doPost("/SubmitBatch", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public GetFieldsResult getFields(GetFieldsParameters params) {
        GetFieldsResult result = null;
        try {
            result = (GetFieldsResult) doPost("/GetFields", params);
        } catch (ClientException e) {
            logger.log(Level.SEVERE, e.toString());
            logger.log(Level.FINE, "STACKTRACE: ", e);
        }
        return result;
    }

    public SearchResult search(SearchParameters params) throws ClientException {
        SearchResult result;
        try {
            result = (SearchResult) doPost("/Search", params);
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

    public DownloadFileResult downloadFile(DownloadFileParameters params) throws ClientException {
        return new DownloadFileResult(doGet(URL_PREFIX + File.separator + params.getUrl()));
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

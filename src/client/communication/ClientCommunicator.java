package client.communication;

import shared.communication.DownloadBatchParameters;
import shared.communication.DownloadBatchResult;
import shared.communication.DownloadFileParameters;
import shared.communication.DownloadFileResult;
import shared.communication.GetFieldsParameters;
import shared.communication.GetFieldsResult;
import shared.communication.GetProjectsParameters;
import shared.communication.GetProjectsResult;
import shared.communication.GetSampleBatchParameters;
import shared.communication.SearchParameters;
import shared.communication.SearchResult;
import shared.communication.SubmitBatchParameters;
import shared.communication.SubmitBatchResult;
import shared.communication.ValidateUserResult;

/**
 * The Class ClientCommunicator.
 */
public class ClientCommunicator {

    /** The server host. */
    private static String       SERVER_HOST = "localhost";

    /** The server port. */
    private static int          SERVER_PORT = 50080;

    /** The url prefix. */
    private static String       URL_PREFIX  = "http://" + SERVER_HOST + ":"
                                              + SERVER_PORT;

    /** The Constant HTTP_GET. */
    private static final String HTTP_GET    = "GET";

    /** The Constant HTTP_POST. */
    private static final String HTTP_POST   = "POST";

    /**
     * Instantiates a new client communicator.
     */
    public ClientCommunicator() {

    }

    /**
     * Instantiates a new client communicator.
     *
     * @param port
     *            the port
     * @param host
     *            the host
     */
    @SuppressWarnings("static-access")
    public ClientCommunicator(String port, String host) {
        this.SERVER_PORT = Integer.parseInt(port);
        this.SERVER_HOST = host;
        this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }

    /**
     * Validate user.
     *
     * @param creds
     *            the creds
     * @return the validate user result
     */
    public ValidateUserResult validateUser(ValidateUserCredentials creds) {
        return null;
    }

    /**
     * Gets the projects.
     *
     * @param creds
     *            the creds
     * @return the projects
     */
    public GetProjectsResult getProjects(GetProjectsParameters creds) {
        return null;
    }

    /**
     * Gets the sample image.
     *
     * @param params
     *            the params
     * @return the sample image
     */
    public GetSampleImageResult getSampleImage(GetSampleBatchParameters params) {
        return null;
    }

    /**
     * Download batch.
     *
     * @param params
     *            the params
     * @return the download batch result
     */
    public DownloadBatchResult downloadBatch(DownloadBatchParameters params) {
        return null;
    }

    /**
     * Gets the fields.
     *
     * @param params
     *            the params
     * @return the fields
     */
    public GetFieldsResult getFields(GetFieldsParameters params) {
        return null;
    }

    /**
     * Search.
     *
     * @param params
     *            the params
     * @return the search result
     */
    public SearchResult search(SearchParameters params) {
        return null;
    }

    /**
     * Download file.
     *
     * @param params
     *            the params
     * @return the download file result
     */
    public DownloadFileResult downloadFile(DownloadFileParameters params) {
        return null;
    }

    /**
     * Submit batch.
     *
     * @param params
     *            the params
     * @return the submit batch result
     */
    public SubmitBatchResult submitBatch(SubmitBatchParameters params) {
        return null;
    }

}

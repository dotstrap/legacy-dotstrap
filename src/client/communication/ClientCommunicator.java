/**
 * ClientCommunicator.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import shared.communication.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientCommunicator.
 */
public class ClientCommunicator {
    
    /** The server port. */
    private static int SERVER_PORT = 50080;
    
    /**
     * Instantiates a new client communicator.
     */
    public ClientCommunicator() {
        
    }
    
    /**
     * Instantiates a new client communicator.
     *
     * @param port the port
     * @param host the host
     */
    @SuppressWarnings("static-access")
    public ClientCommunicator(String port, String host) {
        SERVER_PORT = Integer.parseInt(port);
    }
    
    /**
     * Download batch.
     *
     * @param params the params
     * @return the download batch result
     */
    public DownloadBatchResult downloadBatch(DownloadBatchParameters params) {
        return null;
    }
    
    /**
     * Download file.
     *
     * @param params the params
     * @return the download file result
     */
    public DownloadFileResult downloadFile(DownloadFileParameters params) {
        return null;
    }
    
    /**
     * Gets the fields.
     *
     * @param params the params
     * @return the fields
     */
    public GetFieldsResult getFields(GetFieldsParameters params) {
        return null;
    }
    
    /**
     * Gets the projects.
     *
     * @param creds the creds
     * @return the projects
     */
    public GetProjectsResult getProjects(GetProjectsParameters creds) {
        return null;
    }
    
    /**
     * Gets the sample image.
     *
     * @param params the params
     * @return the sample image
     */
    public GetSampleImageResult getSampleImage(GetSampleImageParameters params) {
        return null;
    }
    
    /**
     * Search.
     *
     * @param params the params
     * @return the search result
     */
    public SearchResult search(SearchParameters params) {
        return null;
    }
    
    /**
     * Submit batch.
     *
     * @param params the params
     * @return the submit batch result
     */
    public SubmitBatchResult submitBatch(SubmitBatchParameters params) {
        return null;
    }
    
    /**
     * Validate user.
     *
     * @param creds the creds
     * @return the validate user result
     */
    public ValidateUserResult validateUser(ValidateUserCredentials creds) {
        return null;
    }
    
}

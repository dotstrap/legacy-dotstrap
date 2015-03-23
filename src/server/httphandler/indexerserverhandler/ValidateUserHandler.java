/**
 * DownloadBatchHandler.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.InvalidCredentialsException;
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;

public class ValidateUserHandler extends IndexerServerHandler {
    // TODO: try to use the authentication method for this??
    /*
     * (non-Javadoc)
     *
     * @see server.httphandler.IndexerServerHandler#doRequest()
     */
    @Override
    protected int doRequest() throws ServerException, DatabaseException,
                    InvalidCredentialsException {
        ValidateUserRequest request = (ValidateUserRequest) getRequest();
        ValidateUserResponse response;

        response = ServerFacade.validateUser(request);
        this.setResponse(response);

        return HttpURLConnection.HTTP_OK;
    }
}

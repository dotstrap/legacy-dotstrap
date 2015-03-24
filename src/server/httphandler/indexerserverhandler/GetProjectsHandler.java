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
import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;

public class GetProjectsHandler extends IndexerServerHandler {

  /*
   * (non-Javadoc)
   *
   * @see server.httphandler.IndexerServerHandler#doRequest()
   */
  @Override
  protected int doRequest() throws ServerException, DatabaseException, InvalidCredentialsException {
    final GetProjectsRequest request = (GetProjectsRequest) getRequest();
    GetProjectsResponse response;

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      response = ServerFacade.getProjects(request);

      this.setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
      this.setResponse(null); // TODO: should I do this?
    }
    return statusCode;
  }
}

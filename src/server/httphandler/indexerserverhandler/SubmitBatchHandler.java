/**
 * SubmitBatchHandler.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.InvalidCredentialsException;
import shared.communication.SubmitBatchRequest;

/**
 * The Class SubmitBatchHandler.
 */
public class SubmitBatchHandler extends IndexerServerHandler {

  /*
   * (non-Javadoc)
   * 
   * @see server.httphandler.IndexerServerHandler#doRequest()
   */
  @Override
  protected int doRequest() throws ServerException, DatabaseException, InvalidCredentialsException {
    SubmitBatchRequest request = (SubmitBatchRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      ServerFacade.submitBatch(request);

    } else {
      statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
      this.setResponse(null); // TODO: should I do this?
    }
    return statusCode;
  }
}

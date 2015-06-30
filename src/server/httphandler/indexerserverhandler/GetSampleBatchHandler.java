/**
 * GetSampleBatchHandler.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;

/**
 * The Class GetSampleBatchHandler.
 */
public class GetSampleBatchHandler extends IndexerServerHandler {

  /*
   * (non-Javadoc)
   * 
   * @see server.httphandler.IndexerServerHandler#doRequest()
   */
  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    GetSampleBatchRequest request = (GetSampleBatchRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(),
        request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      GetSampleBatchResponse response = ServerFacade.getSampleBatch(request);

      setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_OK;
      this.setResponse(null);
    }
    return statusCode;
  }
}


package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.DownloadBatchRequest;
import shared.communication.DownloadBatchResponse;


public class DownloadBatchHandler extends IndexerServerHandler {
  
  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    DownloadBatchRequest request = (DownloadBatchRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      DownloadBatchResponse response = ServerFacade.downloadBatch(request);

      this.setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_OK;
      this.setResponse(null);
    }
    return statusCode;
  }
}

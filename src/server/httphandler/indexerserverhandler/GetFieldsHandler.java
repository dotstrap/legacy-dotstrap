
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.GetFieldsRequest;
import shared.communication.GetFieldsResponse;


public class GetFieldsHandler extends IndexerServerHandler {
  
  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    GetFieldsRequest request = (GetFieldsRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      GetFieldsResponse response = ServerFacade.getFields(request);

      this.setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_OK;
      this.setResponse(null);
    }
    return statusCode;
  }
}

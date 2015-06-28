
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;


public class GetProjectsHandler extends IndexerServerHandler {
  
  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    GetProjectsRequest request = (GetProjectsRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      GetProjectsResponse response = ServerFacade.getProjects(request);

      this.setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_OK;
      this.setResponse(null);
    }
    return statusCode;
  }
}

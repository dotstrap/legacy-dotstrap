
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.SearchRequest;
import shared.communication.SearchResponse;


public class SearchHandler extends IndexerServerHandler {
  
  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    SearchRequest request = (SearchRequest) getRequest();

    int statusCode;
    if (IndexerServerHandler.authenticate(request.getUsername(), request.getPassword())) {
      statusCode = HttpURLConnection.HTTP_OK;

      SearchResponse response = ServerFacade.search(request);

      this.setResponse(response);
    } else {
      statusCode = HttpURLConnection.HTTP_OK;
      this.setResponse(null);
    }
    return statusCode;
  }
}

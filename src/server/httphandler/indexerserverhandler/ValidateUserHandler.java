
package server.httphandler.indexerserverhandler;

import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.httphandler.IndexerServerHandler;

import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;


public class ValidateUserHandler extends IndexerServerHandler {

  @Override
  protected int doRequest() throws ServerException, DatabaseException {
    ValidateUserRequest request = (ValidateUserRequest) getRequest();

    ValidateUserResponse response;
    response = ServerFacade.validateUser(request);

    this.setResponse(response);

    // return response.getUser() != null ? HttpURLConnection.HTTP_OK :
    // HttpURLConnection.HTTP_UNAUTHORIZED;
    return HttpURLConnection.HTTP_OK;
  }
}

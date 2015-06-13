package client.model;

import java.net.URL;
import java.util.logging.Level;

import client.communication.ClientCommunicator;
import client.util.ClientLogManager;
import client.view.IndexerFrame;

import shared.communication.GetProjectsRequest;
import shared.communication.GetProjectsResponse;
import shared.communication.GetSampleBatchRequest;
import shared.communication.GetSampleBatchResponse;
import shared.communication.ValidateUserRequest;
import shared.communication.ValidateUserResponse;
import shared.model.Project;
import shared.model.User;

//@formatter:off
/**
 * Client Facade (Singleton)
 *
 * communicates with the server via the ClientCommunicator class
 */
public enum Facade {
  INSTANCE;

  private static User         user;
  private static String       address;
  private static String       port;
  private static IndexerFrame mainframe;
  private static BatchState   batchState;
  private static ClientCommunicator clientComm = new ClientCommunicator();
  //@formatter:on

  /**
   * Validates the user's credentials with the server
   *
   * @param u the user's username
   * @param p the user's password
   * @return true/false if authentication was successful
   */
  public static boolean validateUser(String u, char[] p) {
    try {
      ValidateUserResponse response =
          clientComm.validateUser(new ValidateUserRequest(u, String.valueOf(p)));
      user = response.getUser();
      return response.isValidated();
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return false;
    }
  }

  public static Project[] getProjects() {
    try {
      GetProjectsResponse response =
          clientComm.getProjects(new GetProjectsRequest(user.getUsername(), user.getPassword()));
      return response.getProjects().toArray(new Project[response.getProjects().size()]);
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static URL getSampleBatch(int projId) {
    try {
      GetSampleBatchResponse response =
          clientComm.getSampleBatch(new GetSampleBatchRequest(user.getUsername(), user.getPassword(), projId));
      String batchUrl = response.getURL().toString() + "/" + response.getSampleBatch().getFilePath();
      return new URL(batchUrl);
    } catch (Exception e) {
      ClientLogManager.getLogger().log(Level.FINE, "STACKTRACE: ", e);
      return null;
    }
  }

  public static User getUser() {
    return user;
  }

  public static String getAddress() {
    return address;
  }

  public static String getPort() {
    return port;
  }

  public static IndexerFrame getMainframe() {
    return mainframe;
  }

  public static BatchState getBatchState() {
    return batchState;
  }

  public static ClientCommunicator getClientComm() {
    return clientComm;
  }
}

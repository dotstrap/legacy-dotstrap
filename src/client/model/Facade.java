package client.model;

import java.io.IOException;

import shared.communication.*;

import client.ClientException;
import client.communication.*;
import client.view.IndexerFrame;

import server.ServerException;

public class Facade {
  private String             address;
  private String             port;
  private String             firstname;
  private String             lastname;
  private String             username;
  private char[]             password;
  private IndexerFrame       mainframe;
  private BatchState         batchState;
  private int                completedRecordCount;

  private ClientCommunicator clientComm;

  /**
   * Instantiates a new Facade.
   *
   * @param address
   * @param port
   */
  public Facade(String address, String port) {
    this.address = address;
    this.port = port;
    this.clientComm = new ClientCommunicator(address, port);
  }

  public boolean validateUser(String u, char[] p) {
    try {
      this.username = u;
      this.password = p;

      ValidateUserResponse response =
          this.clientComm.validateUser(new ValidateUserRequest(username, new String(password)));

      this.firstname = response.getUser().getFirst();
      this.lastname = response.getUser().getLast();
      this.completedRecordCount = response.getUser().getRecordCount();

      return response.isValidated();
    } catch (ServerException e) {
      e.printStackTrace();
    }
    return false;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPort() {
    return this.port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public char[] getPassword() {
    return this.password;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }

  public IndexerFrame getMainframe() {
    return this.mainframe;
  }

  public void setMainframe(IndexerFrame mainframe) {
    this.mainframe = mainframe;
  }

  public BatchState getBatchState() {
    return this.batchState;
  }

  public void setBatchState(BatchState batchState) {
    this.batchState = batchState;
  }

  public int getCompletedRecordCount() {
    return this.completedRecordCount;
  }

  public void setCompletedRecordCount(int completedRecordCount) {
    this.completedRecordCount = completedRecordCount;
  }
}

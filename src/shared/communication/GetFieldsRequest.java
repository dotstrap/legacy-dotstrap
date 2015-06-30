/**
 * GetFieldsRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class GetFieldsRequest.
 */
public class GetFieldsRequest implements Request {
  private String username;
  private String password;
  private int projectId;

  /**
   * Instantiates a new gets the fields request.
   */
  public GetFieldsRequest() {}

  /**
   * Instantiates a new gets the fields request.
   *
   * @param name the name
   * @param password the password
   */
  public GetFieldsRequest(String name, String password) {
    username = name;
    this.password = password;
  }

  /**
   * Instantiates a new gets the fields request.
   *
   * @param name the name
   * @param password the password
   * @param projectId the project id
   */
  public GetFieldsRequest(String name, String password, int projectId) {
    username = name;
    this.password = password;
    this.projectId = projectId;
  }

  public String getPassword() {
    return password;
  }

  public int getProjectId() {
    return projectId;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setUsername(String name) {
    username = name;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return username + "\n" + password + "\n" + projectId + "\n";
  }
}

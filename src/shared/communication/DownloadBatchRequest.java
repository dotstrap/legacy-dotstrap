/**
 * DownloadBatchRequest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadBatchParameters.
 */
public class DownloadBatchRequest implements Request {
  private String username;
  private String password;
  private int    projectId;

  /**
   * Instantiates a new download batch parameters.
   */
  public DownloadBatchRequest() {

  }

  /**
   * Instantiates a new download batch parameters.
   *
   * @param name the name
   * @param password the password
   * @param projectId the project id
   */
  public DownloadBatchRequest(String name, String password, int projectId) {
    username = name;
    this.password = password;
    this.projectId = projectId;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setUsername(String name) {
    username = name;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the project id.
   *
   * @return the project id
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * Sets the project id.
   *
   * @param projectId the new project id
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n" + this.projectId + "\n";
  }

}

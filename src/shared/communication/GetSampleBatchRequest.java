/**
 * GetSampleBatchRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class GetSampleBatchRequest.
 */
public class GetSampleBatchRequest implements Request {
  private String username;
  private String password;
  private int projectId;

  /**
   * Instantiates a new gets the sample batch request.
   */
  public GetSampleBatchRequest() {}

  /**
   * Instantiates a new gets the sample batch request.
   *
   * @param usrname the usrname
   * @param passwd the passwd
   * @param projId the proj id
   */
  public GetSampleBatchRequest(String usrname, String passwd, int projId) {
    username = usrname;
    password = passwd;
    projectId = projId;
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

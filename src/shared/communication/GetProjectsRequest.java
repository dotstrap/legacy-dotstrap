/**
 * GetProjectsRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class GetProjectsRequest.
 */
public class GetProjectsRequest implements Request {
  private String username;
  private String password;

  /**
   * Instantiates a new gets the projects request.
   */
  public GetProjectsRequest() {
    username = "newuser";
    password = "changeme";
  }

  /**
   * Instantiates a new gets the projects request.
   *
   * @param usrname the usrname
   * @param passwrd the passwrd
   */
  public GetProjectsRequest(String usrname, String passwrd) {
    username = usrname;
    password = passwrd;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String usrname) {
    username = usrname;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return username + "\n" + password + "\n";
  }
}

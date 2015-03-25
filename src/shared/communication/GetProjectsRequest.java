/**
 * GetProjectsRequest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsRequest.
 */
public class GetProjectsRequest implements Request {
  private String username;
  private String password;

  /**
   * Instantiates GetProjectsRequest
   */
  public GetProjectsRequest() {
    username = "newuser";
    password = "changeme";
  }

  /**
   * Instantiates GetProjectsRequest
   *
   * @param usrname the name
   * @param passwrd the password
   */
  public GetProjectsRequest(String usrname, String passwrd) {
    this.username = usrname;
    this.password = passwrd;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String usrname) {
    username = usrname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n";
  }
}

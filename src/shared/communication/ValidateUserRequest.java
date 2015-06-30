/**
 * ValidateUserRequest.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class ValidateUserRequest.
 */
public class ValidateUserRequest implements Request {

  private String username;

  private String password;

  /**
   * Instantiates a new validate user request.
   */
  public ValidateUserRequest() {
    username = "";
    password = "";
  }

  /**
   * Instantiates a new validate user request.
   *
   * @param u the u
   * @param p the p
   */
  public ValidateUserRequest(String u, String p) {
    username = u;
    password = p;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String s) {
    password = s;
  }

  public void setUsername(String s) {
    username = s;
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

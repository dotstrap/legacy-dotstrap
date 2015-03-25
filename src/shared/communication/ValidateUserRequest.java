/**
 * ValidateUserRequest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserRequest.
 */
public class ValidateUserRequest implements Request {
  private String username;
  private String password;

  /**
   * Instantiates a new ValidateUserRequest.
   *
   */
  public ValidateUserRequest() {
    username = "";
    password = "";
  }

  /**
   * Instantiates a new validate user credentials.
   *
   * @param u the u
   * @param p the p
   */
  public ValidateUserRequest(String u, String p) {
    username = u;
    password = p;
  }

  /**
   * gets username.
   *
   * @return -> returns the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * sets username.
   *
   * @param s -> new username
   */
  public void setUsername(String s) {
    username = s;
  }

  /**
   * gets password.
   *
   * @return -> returns the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * sets password.
   *
   * @param s -> new password
   */
  public void setPassword(String s) {
    password = s;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n";
  }
}

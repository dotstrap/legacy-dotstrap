/**
 * ValidateUserResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserResponse.
 */
public class ValidateUserResponse implements Response {
  private User    user;
  private boolean isValid;

  /**
   * Instantiates a new validate user result.
   */
  public ValidateUserResponse() {
    isValid = false;
  }

  /**
   * Instantiates a new ValidateUserResponse.
   *
   * @param user
   */
  public ValidateUserResponse(User user) {
    this.user = user;
  }

  /**
   * Instantiates a new ValidateUserResponse.
   *
   * @param user the user
   * @param isValid the is valid
   */
  public ValidateUserResponse(User user, boolean isValid) {
    this.user = user;
    this.isValid = isValid;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  /**
   *
   * (non-Javadoc).
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("TRUE\n");
    output.append(user.getFirst() + "\n");
    output.append(user.getLast() + "\n");
    output.append(user.getRecordCount() + "\n");

    return output.toString();
  }
}

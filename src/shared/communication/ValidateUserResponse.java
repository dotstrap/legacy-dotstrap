/**
 * ValidateUserResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import shared.model.User;

/**
 * The Class ValidateUserResponse.
 */
public class ValidateUserResponse implements Response {
  private User user;

  /**
   * Instantiates a new validate user result.
   */
  public ValidateUserResponse() {
    user = null;
  }

  /**
   * Instantiates a new ValidateUserResponse.
   *
   * @param user
   */
  public ValidateUserResponse(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    if (user != null) {
      output.append("TRUE\n");
      output.append(user.getFirst() + "\n");
      output.append(user.getLast() + "\n");
      output.append(user.getRecordCount() + "\n");
    } else {
      output.append("FALSE\n");
    }
    return output.toString();
  }
}

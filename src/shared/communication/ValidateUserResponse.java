/**
 * ValidateUserResponse.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
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
   * Instantiates a new validate user response.
   */
  public ValidateUserResponse() {
    user = null;
  }

  /**
   * Instantiates a new validate user response.
   *
   * @param user the user
   */
  public ValidateUserResponse(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public boolean isValidated() {
    return user != null;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /*
   * (non-Javadoc)
   * 
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

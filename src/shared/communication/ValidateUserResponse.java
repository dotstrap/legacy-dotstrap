package shared.communication;

import shared.model.User;

public class ValidateUserResponse implements Response {
  private User user;

  public ValidateUserResponse() {
    user = null;
  }

  public ValidateUserResponse(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isValidated() {
    return this.user != null;
  }

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

package shared.communication;

public class ValidateUserRequest implements Request {
  private String username;
  private String password;

  public ValidateUserRequest() {
    username = "";
    password = "";
  }

  public ValidateUserRequest(String u, String p) {
    username = u;
    password = p;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String s) {
    username = s;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String s) {
    password = s;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n";
  }
}

package shared.communication;

public class GetProjectsRequest implements Request {
  private String username;
  private String password;

  public GetProjectsRequest() {
    username = "newuser";
    password = "changeme";
  }

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

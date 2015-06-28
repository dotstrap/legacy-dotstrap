package shared.communication;

public class DownloadBatchRequest implements Request {
  private String username;
  private String password;
  private int projectId;

  public DownloadBatchRequest() {}

  public DownloadBatchRequest(String name, String password, int projectId) {
    username = name;
    this.password = password;
    this.projectId = projectId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String name) {
    username = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n" + this.projectId + "\n";
  }

}

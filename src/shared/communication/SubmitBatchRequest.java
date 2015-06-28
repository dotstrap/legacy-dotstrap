package shared.communication;

public class SubmitBatchRequest implements Request {
  private String username;
  private String password;
  private int batchID;
  private String fieldValues;

  public SubmitBatchRequest(String username, String password, int batchID, String fieldValues) {
    this.username = username;
    this.password = password;
    this.batchID = batchID;
    this.fieldValues = fieldValues;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getBatchID() {
    return this.batchID;
  }

  public void setBatchID(int batchID) {
    this.batchID = batchID;
  }

  public String getFieldValues() {
    return this.fieldValues;
  }

  public void setFieldValues(String fieldValues) {
    this.fieldValues = fieldValues;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n" + this.batchID + "\n" + this.fieldValues
        + "\n";
  }
}

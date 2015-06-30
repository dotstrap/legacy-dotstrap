/**
 * SubmitBatchRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class SubmitBatchRequest.
 */
public class SubmitBatchRequest implements Request {

  private String username;

  private String password;

  private int batchID;

  private String fieldValues;

  /**
   * Instantiates a new submit batch request.
   *
   * @param username the username
   * @param password the password
   * @param batchID the batch id
   * @param fieldValues the field values
   */
  public SubmitBatchRequest(String username, String password, int batchID,
      String fieldValues) {
    this.username = username;
    this.password = password;
    this.batchID = batchID;
    this.fieldValues = fieldValues;
  }

  public int getBatchID() {
    return batchID;
  }

  public String getFieldValues() {
    return fieldValues;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setBatchID(int batchID) {
    this.batchID = batchID;
  }

  public void setFieldValues(String fieldValues) {
    this.fieldValues = fieldValues;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(username);
    sb.append("\n");
    sb.append(password);
    sb.append("\n");
    sb.append(batchID);
    sb.append("\n");
    sb.append(fieldValues);
    sb.append("\n");
    return sb.toString();
  }

}

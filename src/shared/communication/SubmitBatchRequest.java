/**
 * SubmitBatchRequest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;


/**
 * The Class SubmitBatchRequest.
 */
public class SubmitBatchRequest implements Request {
  private String username;
  private String password;
  private int    batchID;
  private String fieldValues;

  /**
   * Instantiates a new SubmitBatch Request.
   *
   * @param username the username
   * @param password the password
   * @param batchID the batch id
   * @param fieldValues the field values
   */
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

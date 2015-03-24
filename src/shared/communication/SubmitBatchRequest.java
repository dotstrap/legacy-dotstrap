/**
 * SubmitBatchRequest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchRequest.
 */
public class SubmitBatchRequest implements Request {
  private String username;
  private String password;
  private int batchId;
  private List<Record> fieldValues;

  /**
   * @param username
   * @param password
   * @param batchId
   * @param fieldValues
   */
  public SubmitBatchRequest(String username, String password, int batchId, List<Record> fieldValues) {
    this.username = username;
    this.password = password;
    this.batchId = batchId;
    this.fieldValues = fieldValues;
  }

  /**
   * Instantiates a new submit batch parameters.
   */
  public SubmitBatchRequest() {

  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the batchId
   */
  public int getBatchId() {
    return batchId;
  }

  /**
   * @param batchId the batchId to set
   */
  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  /**
   * @return the fieldValues
   */
  public List<Record> getFieldValues() {
    return fieldValues;
  }

  /**
   * @param fieldValues the fieldValues to set
   */
  public void setFieldValues(List<Record> fieldValues) {
    this.fieldValues = fieldValues;
  }

}

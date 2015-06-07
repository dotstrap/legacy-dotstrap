/**
 * Batch.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Batch (image).
 */
public class Batch {
  /** Code for a batch that has not been indexed yet. */
  public static int INCOMPLETE = 0;
  /** Code for a batch that is currently being indexed. */
  public static int ACTIVE     = 1;
  /** Code for a batch that has been indexed. */
  public static int COMPLETE   = 2;

  private int       batchId;// @formatter:off
  private String filePath;
  private int    projectId;
  private int    status;
  private int    currUserId;

  /**
   * Instantiates a new Batch without params.
   */
  public Batch() {
    batchId    = -1;
    filePath   = "filePath";
    projectId  = -1;
    status     = INCOMPLETE;
    currUserId = -1;
  }

  /**
   * Instantiates a new Batch.
   *
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   */
  public Batch(String filePath, int projectId, int status) {
    this.batchId    = -1;
    this.filePath   = filePath;
    this.projectId  = projectId;
    this.status     = status;
    this.currUserId = -1;
  }

  /**
   * Instantiates a new Batch.
   *
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   * @param currUserId the curr user id
   */
  public Batch(String filePath, int projectId, int status, int currUserId) {
    this.batchId    = -1;
    this.filePath   = filePath;
    this.projectId  = projectId;
    this.status     = status;
    this.currUserId = currUserId;
  }

  /**
   * Instantiates a new Batch.
   *
   * @param batchId the batch id
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   * @param currUserId the curr user id
   */
  public Batch(int batchId, String filePath, int projectId, int status, int currUserId) {
    this.batchId    = batchId;
    this.filePath   = filePath;
    this.projectId  = projectId;
    this.status     = status;
    this.currUserId = currUserId;// @formatter:on
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getCurrUserId() {
    return currUserId;
  }

  public void setCurrUserId(int currUserId) {
    this.currUserId = currUserId;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o.getClass() != this.getClass())
      return false;
    if (o == this)
      return true;

    Batch other = (Batch) o;
    return (batchId == other.getBatchId()// @formatter:off
              && projectId == other.getProjectId()
              && filePath.equals(other.getFilePath())
//            && Status == other.getstatus()
              && currUserId == other.getCurrUserId()); // @formatter:on
  }

}

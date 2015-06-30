/**
 * Batch.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Batch.
 */
public class Batch {
  public static int INCOMPLETE = 0;
  public static int ACTIVE = 1;
  public static int COMPLETE = 2;

  private int batchId;
  private String filePath;
  private int projectId;
  private int status;
  private int currUserId;

  /**
   * Instantiates a new batch.
   */
  public Batch() {
    batchId = -1;
    filePath = "filePath";
    projectId = -1;
    status = INCOMPLETE;
    currUserId = -1;
  }

  /**
   * Instantiates a new batch.
   *
   * @param batchId the batch id
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   * @param currUserId the curr user id
   */
  public Batch(int batchId, String filePath, int projectId, int status,
      int currUserId) {
    this.batchId = batchId;
    this.filePath = filePath;
    this.projectId = projectId;
    this.status = status;
    this.currUserId = currUserId;
  }

  /**
   * Instantiates a new batch.
   *
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   */
  public Batch(String filePath, int projectId, int status) {
    batchId = -1;
    this.filePath = filePath;
    this.projectId = projectId;
    this.status = status;
    currUserId = -1;
  }

  /**
   * Instantiates a new batch.
   *
   * @param filePath the file path
   * @param projectId the project id
   * @param status the status
   * @param currUserId the curr user id
   */
  public Batch(String filePath, int projectId, int status, int currUserId) {
    batchId = -1;
    this.filePath = filePath;
    this.projectId = projectId;
    this.status = status;
    this.currUserId = currUserId;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    if (o == this) {
      return true;
    }

    Batch other = (Batch) o;
    return ((batchId == other.getBatchId())
        && (projectId == other.getProjectId())
        && filePath.equals(other.getFilePath())
        && (currUserId == other.getCurrUserId()));
  }

  public int getBatchId() {
    return batchId;
  }

  public int getCurrUserId() {
    return currUserId;
  }

  public String getFilePath() {
    return filePath;
  }

  public int getProjectId() {
    return projectId;
  }

  public int getStatus() {
    return status;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  public void setCurrUserId(int currUserId) {
    this.currUserId = currUserId;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Batch [batchId=");
    sb.append(batchId);
    sb.append(", filePath=");
    sb.append(filePath);
    sb.append(", projectId=");
    sb.append(projectId);
    sb.append(", status=");
    sb.append(status);
    sb.append(", currUserId=");
    sb.append(currUserId);
    sb.append("]");
    return sb.toString();
  }

}

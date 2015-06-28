package shared.model;

public class Batch {

  public static int INCOMPLETE = 0;

  public static int ACTIVE = 1;

  public static int COMPLETE = 2;

  private int batchId;// @formatter:off
  private String filePath;
  private int    projectId;
  private int    status;
  private int    currUserId;

  
  public Batch() {
    batchId    = -1;
    filePath   = "filePath";
    projectId  = -1;
    status     = INCOMPLETE;
    currUserId = -1;
  }

  
  public Batch(String filePath, int projectId, int status) {
    this.batchId    = -1;
    this.filePath   = filePath;
    this.projectId  = projectId;
    this.status     = status;
    this.currUserId = -1;
  }

  
  public Batch(String filePath, int projectId, int status, int currUserId) {
    this.batchId    = -1;
    this.filePath   = filePath;
    this.projectId  = projectId;
    this.status     = status;
    this.currUserId = currUserId;
  }

  
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Batch [batchId=");
    builder.append(this.batchId);
    builder.append(", filePath=");
    builder.append(this.filePath);
    builder.append(", projectId=");
    builder.append(this.projectId);
    builder.append(", status=");
    builder.append(this.status);
    builder.append(", currUserId=");
    builder.append(this.currUserId);
    builder.append("]");
    return builder.toString();
  }

}

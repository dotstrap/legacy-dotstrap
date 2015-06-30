/**
 * SubmitBatchResponse.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class SubmitBatchResponse.
 */
public class SubmitBatchResponse implements Response {

  private boolean success;

  /**
   * Instantiates a new submit batch response.
   */
  public SubmitBatchResponse() {
    success = false;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (success) {
      sb.append("TRUE\n");
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}

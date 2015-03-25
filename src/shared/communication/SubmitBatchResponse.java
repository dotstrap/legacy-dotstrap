/**
 * SubmitBatchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchResponse.
 */
public class SubmitBatchResponse implements Response {

  /** The success. */
  private boolean success;

  /**
   * Instantiates a new submit batch result.
   */
  public SubmitBatchResponse() {
    success = false;
  }

  /**
   * Checks if is success.
   *
   * @return true, if is success
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * Sets the success.
   *
   * @param success the new success
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   *
   * (non-Javadoc).
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
//    StringBuilder sb = new StringBuilder();
//
//      sb.append("TRUE\n");
//
    return "TRUE\n";
  }
}

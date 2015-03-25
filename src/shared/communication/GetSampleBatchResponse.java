/**
 * GetSampleBatchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleBatchResponse.
 */
public class GetSampleBatchResponse implements Response {
  private URL urlPrefix;
  private Batch sampleBatch;
  private boolean validUser;

  /**
   * Instantiates a new gets the sample image result.
   */
  public GetSampleBatchResponse() {
    validUser = false;
  }

  /**
   * Sets the urlPrefix.
   *
   * @param urlPrefix the new urlPrefix
   */
  public void setUrlPrefix(URL url) {
    urlPrefix = url;
  }

  /**
   * Gets the urlPrefix.
   *
   * @return the urlPrefix
   */
  public URL getURL() {
    return urlPrefix;
  }

  /**
   * @return the sampleBatch
   */
  public Batch getSampleBatch() {
    return sampleBatch;
  }

  /**
   * @param sampleBatch the sampleBatch to set
   */
  public void setSampleBatch(Batch sampleBatch) {
    this.sampleBatch = sampleBatch;
  }

  /**
   * Checks if is valid user.
   *
   * @return true, if is valid user
   */
  public boolean isValidUser() {
    return validUser;
  }

  /**
   * Sets the valid user.
   *
   * @param validUser the new valid user
   */
  public void setValidUser(boolean validUser) {
    this.validUser = validUser;
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
    StringBuilder sb = new StringBuilder();
    sb.append(urlPrefix + "/" + sampleBatch.getFilePath() + "\n");
    return sb.toString();
  }
}

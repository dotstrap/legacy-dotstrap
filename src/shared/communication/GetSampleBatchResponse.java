/**
 * GetSampleBatchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;

import shared.model.Batch;

/**
 * The Class GetSampleBatchResponse.
 */
public class GetSampleBatchResponse implements Response {
  private URL   urlPrefix;
  private Batch sampleBatch;

  /**
   * Instantiates a new gets the sample image result.
   */
  public GetSampleBatchResponse() {
    sampleBatch = null;
  }

  public GetSampleBatchResponse(Batch sampleBatch) {
      this.sampleBatch = sampleBatch;
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
   *
   * (non-Javadoc).
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (sampleBatch != null) {
      sb.append(urlPrefix + "/" + sampleBatch.getFilePath() + "\n");
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}

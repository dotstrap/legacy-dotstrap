/**
 * GetSampleBatchResponse.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;

import shared.model.Batch;

/**
 * The Class GetSampleBatchResponse.
 */
public class GetSampleBatchResponse implements Response {
  private URL urlPrefix;
  private Batch sampleBatch;

  /**
   * Instantiates a new gets the sample batch response.
   */
  public GetSampleBatchResponse() {
    sampleBatch = null;
  }

  /**
   * Instantiates a new gets the sample batch response.
   *
   * @param sampleBatch the sample batch
   */
  public GetSampleBatchResponse(Batch sampleBatch) {
    this.sampleBatch = sampleBatch;
  }

  public Batch getSampleBatch() {
    return sampleBatch;
  }

  public URL getURL() {
    return urlPrefix;
  }

  public void setSampleBatch(Batch sampleBatch) {
    this.sampleBatch = sampleBatch;
  }

  public void setUrlPrefix(URL url) {
    urlPrefix = url;
  }

  /*
   * (non-Javadoc)
   *
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

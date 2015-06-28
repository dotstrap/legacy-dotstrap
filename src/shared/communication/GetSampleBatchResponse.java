package shared.communication;

import java.net.URL;

import shared.model.Batch;

public class GetSampleBatchResponse implements Response {
  private URL urlPrefix;
  private Batch sampleBatch;

  public GetSampleBatchResponse() {
    sampleBatch = null;
  }

  public GetSampleBatchResponse(Batch sampleBatch) {
    this.sampleBatch = sampleBatch;
  }

  public void setUrlPrefix(URL url) {
    urlPrefix = url;
  }

  public URL getURL() {
    return urlPrefix;
  }

  public Batch getSampleBatch() {
    return sampleBatch;
  }

  public void setSampleBatch(Batch sampleBatch) {
    this.sampleBatch = sampleBatch;
  }

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

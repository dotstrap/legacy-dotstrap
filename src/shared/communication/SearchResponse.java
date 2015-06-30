/**
 * SearchResponse.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;
import java.util.List;

import shared.model.Record;

/**
 * The Class SearchResponse.
 */
public class SearchResponse implements Response {

  private List<Record> foundRecords;

  private URL urlPrefix;

  /**
   * Instantiates a new search response.
   */
  public SearchResponse() {
    foundRecords = null;
  }

  /**
   * Instantiates a new search response.
   *
   * @param foundRecords the found records
   */
  public SearchResponse(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  public List<Record> getFoundRecords() {
    return foundRecords;
  }

  public URL getUrlPrefix() {
    return urlPrefix;
  }

  public void setFoundRecords(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  public void setUrlPrefix(URL urlPrefix) {
    this.urlPrefix = urlPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (foundRecords.size() > 0) {
      for (int i = 0; i < foundRecords.size(); ++i) {
        sb.append(foundRecords.get(i).getBatchId() + "\n");
        sb.append(urlPrefix + "/" + foundRecords.get(i).getBatchURL() + "\n");
        sb.append(foundRecords.get(i).getRowNum() + "\n");
        sb.append(foundRecords.get(i).getFieldId() + "\n");
      }
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}

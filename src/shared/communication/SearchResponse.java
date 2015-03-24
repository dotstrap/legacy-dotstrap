/**
 * SearchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
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
  private List<URL> urls;

  /**
   * Instantiates a new SearchResponse.
   *
   */
  public SearchResponse() {
    foundRecords = null;
  }

  /**
   * @param foundRecords
   */
  public SearchResponse(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  /**
   * @return the foundRecords
   */
  public List<Record> getFoundRecords() {
    return foundRecords;
  }

  /**
   * @param foundRecords the foundRecords to set
   */
  public void setFoundRecords(List<Record> foundRecords) {
    this.foundRecords = foundRecords;
  }

  public List<URL> getUrls() {
    return urls;
  }

  public void setUrls(List<URL> urls) {
    this.urls = urls;
  }

  /**
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < foundRecords.size(); ++i) {
      sb.append(foundRecords.get(i).getBatchId() + "\n");
      sb.append(urls.get(i) + "\n");
      sb.append(foundRecords.get(i).getRowNum() + "\n");
      sb.append(foundRecords.get(i).getFieldId() + "\n");
    }
    return sb.toString();
  }

}

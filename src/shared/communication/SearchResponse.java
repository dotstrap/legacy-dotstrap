/**
 * SearchResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 27, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

import shared.model.Record;

/**
 * The Class SearchResponse.
 */
public class SearchResponse implements Response {
  private List<Record> foundRecords;
  private List<String> urls;

  /**
   * Instantiates a new SearchResponse.
   *
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

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  /**
   * To string.
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (foundRecords.size() > 0) {
      for (int i = 0; i < foundRecords.size(); ++i) {
        sb.append(foundRecords.get(i).getBatchId() + "\n");
        sb.append(urls.get(i) + "\n");
        sb.append(foundRecords.get(i).getRowNum() + "\n");
        sb.append(foundRecords.get(i).getFieldId() + "\n");
      }
    } else {
      sb.append("FAILED\n");
    }
    return sb.toString();
  }
}

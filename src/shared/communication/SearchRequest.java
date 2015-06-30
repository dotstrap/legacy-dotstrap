/**
 * SearchRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

/**
 * The Class SearchRequest.
 */
public class SearchRequest implements Request {
  private String username;
  private String password;
  private List<Integer> fieldIds;
  private List<String> searchQueries;

  /**
   * Instantiates a new search request.
   */
  public SearchRequest() {}

  /**
   * Instantiates a new search request.
   *
   * @param name the name
   * @param password the password
   * @param newFieldIds the new field ids
   * @param newSearchQs the new search qs
   */
  public SearchRequest(String name, String password, List<Integer> newFieldIds,
      List<String> newSearchQs) {// @formatter:off
    username = name;
    this.password = password;
    fieldIds = newFieldIds;
    searchQueries = newSearchQs; // formatter:on
  }

  public List<Integer> getFieldIds() {
    return fieldIds;
  }

  public String getPassword() {
    return password;
  }

  public List<String> getSearchQueries() {
    return searchQueries;
  }

  public String getUsername() {
    return username;
  }

  public void setFieldIds(List<Integer> fieldIds) {
    this.fieldIds = fieldIds;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setSearchQueries(List<String> search) {
    searchQueries = search;
  }

  public void setUsername(String name) {
    username = name;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(username);
    sb.append("\n");
    sb.append(password);
    sb.append("\n");
    sb.append(fieldIds);
    sb.append("\n");
    sb.append(searchQueries);
    sb.append("\n");
    return sb.toString();
  }

}

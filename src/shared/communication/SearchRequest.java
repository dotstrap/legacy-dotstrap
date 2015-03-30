/**
 * SearchRequest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SearchRequest.
 */
public class SearchRequest implements Request {
  private String             username;
  private String             password;
  private List<Integer> fieldIds;
  private List<String>  searchQueries;

  /**
   * Instantiates a new search parameters.
   */
  public SearchRequest() {

  }

  /**
   * Instantiates a new search parameters.
   *
   * @param name the name
   * @param password the password
   * @param newFieldIds the field id
   * @param searchQ the search
   */
  public SearchRequest(String name, String password, List<Integer> newFieldIds,
      List<String> newSearchQs) {//@formatter:off
    //System.out.println("\n=========FIELD IN CONST: " + newFieldIds.toString());
    //System.out.println("\n=========SEARCH PARAM IN CONST: " + newSearchQs.toString());

    this.username      = name;
    this.password      = password;
    this.fieldIds      = newFieldIds;
    this.searchQueries = newSearchQs; // formatter:on
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setUsername(String name) {
    username = name;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the fieldIds
   */
  public List<Integer> getFieldIds() {
    return fieldIds;
  }

  /**
   * @param fieldIds the fieldIds to set
   */
  public void setFieldIds(List<Integer> fieldIds) {
    this.fieldIds = fieldIds;
  }

  /**
   * Gets the search.
   *
   * @return the search
   */
  public List<String> getSearchQueries() {
    return searchQueries;
  }

  /**
   * Sets the search.
   *
   * @param search the new search
   */
  public void setSearchQueries(List<String> search) {
    searchQueries = search;
  }

  @Override
  public String toString() {// @formatter:off
    return this.username + "\n"
         + this.password + "\n"
         + this.fieldIds + "\n"
         + this.searchQueries + "\n"; // @formatter:on
  }
}

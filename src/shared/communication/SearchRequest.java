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

// TODO: Auto-generated Javadoc
/**
 * The Class SearchRequest.
 */
public class SearchRequest implements Request {
  private String            username;
  private String            password;
  private List<Integer>     fieldIds;
  private ArrayList<String> searchQueries;

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
   * @param fieldId the field id
   * @param search the search
   */
  public SearchRequest(String name, String password, ArrayList<Integer> fieldId,
      ArrayList<String> search) {//@formatter:off
    username      = name;
    this.password = password;
    searchQueries = search;
  }

  /**
   * Instantiates a new search request.
   *
   * @param name the name
   * @param password the password
   * @param fieldIds the field ids
   * @param searchQuery the search query
   */
  public SearchRequest(String name, String password,
      List<Integer> fieldIds, ArrayList<String> searchQuery) {
    username      = name;
    this.password = password;
    this.fieldIds = fieldIds;
    searchQueries = searchQuery; //@formatter:on
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
  public ArrayList<String> getSearchQueries() {
    return searchQueries;
  }

  /**
   * Sets the search.
   *
   * @param search the new search
   */
  public void setSearchQueries(ArrayList<String> search) {
    searchQueries = search;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n" + this.fieldIds + "\n" + this.searchQueries
        + "\n";
  }
}

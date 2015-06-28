package shared.communication;

import java.util.ArrayList;
import java.util.List;

public class SearchRequest implements Request {
  private String username;
  private String password;
  private List<Integer> fieldIds;
  private List<String> searchQueries;

  public SearchRequest() {}

  public SearchRequest(String name, String password, List<Integer> newFieldIds,
      List<String> newSearchQs) {//@formatter:off
    //System.out.println("\n=========FIELD IN CONST: " + newFieldIds.toString());
    //System.out.println("\n=========SEARCH PARAM IN CONST: " + newSearchQs.toString());

    this.username      = name;
    this.password      = password;
    this.fieldIds      = newFieldIds;
    this.searchQueries = newSearchQs; // formatter:on
  }

  
  public String getUsername() {
    return username;
  }

  
  public void setUsername(String name) {
    username = name;
  }

  
  public String getPassword() {
    return password;
  }

  
  public void setPassword(String password) {
    this.password = password;
  }

  
  public List<Integer> getFieldIds() {
    return fieldIds;
  }

  
  public void setFieldIds(List<Integer> fieldIds) {
    this.fieldIds = fieldIds;
  }

  
  public List<String> getSearchQueries() {
    return searchQueries;
  }

  
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

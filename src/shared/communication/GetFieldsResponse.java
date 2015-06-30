/**
 * GetFieldsResponse.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;
import shared.model.User;

/**
 * The Class GetFieldsResponse.
 */
public class GetFieldsResponse implements Response {
  private User user;
  private List<Field> fields;

  /**
   * Instantiates a new gets the fields response.
   */
  public GetFieldsResponse() {
    fields = null;
  }

  /**
   * Instantiates a new gets the fields response.
   *
   * @param fields the fields
   */
  public GetFieldsResponse(ArrayList<Field> fields) {
    this.fields = fields;
  }

  public List<Field> getFields() {
    return fields;
  }

  public User getUser() {
    return user;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Field f : fields) {
      sb.append(f.getProjectId() + "\n");
      sb.append(f.getFieldId() + "\n");
      sb.append(f.getTitle() + "\n");
    }
    return sb.toString();
  }
}

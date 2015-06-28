package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;
import shared.model.User;

public class GetFieldsResponse implements Response {
  private User user;
  private List<Field> fields;

  public GetFieldsResponse() {
    fields = null;
  }

  public GetFieldsResponse(ArrayList<Field> fields) {
    this.fields = fields;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  @Override
  public String toString() {
    // if (this.user == null || this.fields == null || this.fields.isEmpty()) {
    // return "FAILED\n";
    // }

    StringBuilder sb = new StringBuilder();
    for (Field f : fields) {
      sb.append(f.getProjectId() + "\n");
      sb.append(f.getFieldId() + "\n");
      sb.append(f.getTitle() + "\n");
    }
    return sb.toString();
  }
}

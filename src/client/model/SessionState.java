package client.model;

import java.net.URL;
import java.util.List;

import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;

public class SessionState {

  private Batch batch;
  private Project project;
  private List<Field> fields;
  private URL batchUrl;

  /**
   * Instantiates a new SessionState.
   *
   */
  public SessionState() {


  }

}

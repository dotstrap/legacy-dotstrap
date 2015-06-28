/**
 * SessionState.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 28, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
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

  public SessionState() {}

}

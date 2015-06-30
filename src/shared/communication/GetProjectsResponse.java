/**
 * GetProjectsResponse.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

import shared.model.Project;

/**
 * The Class GetProjectsResponse.
 */
public class GetProjectsResponse implements Response {
  private List<Project> projects;

  /**
   * Instantiates a new gets the projects response.
   */
  public GetProjectsResponse() {}

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Project p : projects) {
      sb.append(p.getProjectId() + "\n");
      sb.append(p.getTitle() + "\n");
    }
    return sb.toString();
  }
}

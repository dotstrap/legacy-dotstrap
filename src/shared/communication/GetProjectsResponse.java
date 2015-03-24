/**
 * GetProjectsResponse.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsResponse.
 */
public class GetProjectsResponse implements Response {
  /** The projects. */
  private List<Project> projects;

  /**
   * Instantiates a new gets the projects result.
   */
  public GetProjectsResponse() {}

  /**
   * gets all projects.
   *
   * @return -> array of projectinfo if found, else return null
   */
  public List<Project> getProjects() {
    return projects;
  }

  /**
   * replaces projects with new projects.
   *
   * @param projects -> array of new projects with which to replace
   */
  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  /**
   *
   * (non-Javadoc).
   *
   * @return the string
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (final Project p : projects) {
      sb.append(p.getProjectId() + "\n");
      sb.append(p.getTitle() + "\n");
    }
    return sb.toString();
  }
}

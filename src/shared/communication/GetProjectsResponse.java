package shared.communication;

import java.util.List;

import shared.model.Project;

public class GetProjectsResponse implements Response {
  private List<Project> projects;

  public GetProjectsResponse() {}

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

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

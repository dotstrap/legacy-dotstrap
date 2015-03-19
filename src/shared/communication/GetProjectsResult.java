package shared.communication;

import java.util.List;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsResult.
 */
public class GetProjectsResult {

    /** The projects. */
    private List<Project> projects;

    /** The valid user. */
    private boolean            userIsValid;

    /**
     * Instantiates a new gets the projects result.
     */
    public GetProjectsResult() {
        userIsValid = false;
    }

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
     * @param projects            -> array of new projects with which to replace
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Checks if is valid user.
     *
     * @return true, if is valid user
     */
    public boolean isUserIsValid() {
        return userIsValid;
    }

    /**
     * Sets the valid user.
     *
     * @param v the new valid user
     */
    public void setUserIsValid(boolean v) {
        this.userIsValid = v;
    }

    /**
     *
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (userIsValid) {
            for (Project p : projects) {
                sb.append(p.getProjectID() + "\n");
                sb.append(p.getTitle() + "\n");
            }
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}

package shared.communication;

import java.util.ArrayList;

import shared.model.Project;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsResult.
 */
public class GetProjectsResult {

    /** The projects. */
    private ArrayList<Project> projects;

    /** The valid user. */
    private boolean            validUser;

    /**
     * Instantiates a new gets the projects result.
     */
    public GetProjectsResult() {
        validUser = false;
    }

    /**
     * gets all projects.
     *
     * @return -> array of projectinfo if found, else return null
     */
    public ArrayList<Project> getProjects() {
        return projects;
    }

    /**
     * replaces projects with new projects.
     *
     * @param projects
     *            -> array of new projects with which to replace
     */
    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    /**
     * Checks if is valid user.
     *
     * @return true, if is valid user
     */
    public boolean isValidUser() {
        return validUser;
    }

    /**
     * Sets the valid user.
     *
     * @param validUser
     *            the new valid user
     */
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
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
        if (validUser) {
            for (Project p : projects) {
                sb.append(p.getProjInfo().getID() + "\n");
                sb.append(p.getProjInfo().getName() + "\n");
            }
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}

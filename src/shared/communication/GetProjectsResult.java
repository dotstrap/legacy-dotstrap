/**
 * GetProjectsResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
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
    
    public ArrayList<Project> getProjects() {
        return projects;
    }
    
    /**
     * Checks if is valid user.
     */
    public boolean isValidUser() {
        return validUser;
    }
    
    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
    
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }
    
    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
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

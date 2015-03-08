/**
 * GetSampleImageParameters.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleImageParameters.
 */
public class GetSampleImageParameters {
    
    /** The name. */
    private String name;
    
    /** The password. */
    private String password;
    
    /** The project id. */
    private int    projectID;
    
    /**
     * Instantiates a new gets the sample image parameters.
     */
    public GetSampleImageParameters() {
        
    }
    
    /**
     * Instantiates a new gets the sample image parameters.
     *
     * @param name the name
     * @param password the password
     * @param projectID the project id
     */
    public GetSampleImageParameters(String name, String password, int projectID) {
        this.name = name;
        this.password = password;
        this.projectID = projectID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int getProjectID() {
        return projectID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    
}

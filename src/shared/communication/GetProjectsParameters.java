/**
 * GetProjectsParameters.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsParameters.
 */
public class GetProjectsParameters {
    
    /** The name. */
    private String name;
    
    /** The password. */
    private String password;
    
    /**
     * Instantiates a new gets the projects parameters.
     */
    public GetProjectsParameters() {
        
    }
    
    /**
     * Instantiates a new gets the projects parameters.
     *
     * @param name the name
     * @param password the password
     */
    public GetProjectsParameters(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}

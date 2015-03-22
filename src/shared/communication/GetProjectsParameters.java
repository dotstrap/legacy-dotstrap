/**
 * GetProjectsParameters.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetProjectsParameters.
 */
public class GetProjectsParameters {
    /** The name. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Instantiates a new gets the projects parameters.
     */
    public GetProjectsParameters() {
            username = "newuser";
            password = "changeme";
    }

    /**
     * Instantiates a new gets the projects parameters.
     *
     * @param name the name
     * @param password the password
     */
    public GetProjectsParameters(String name, String password) {
        this.username = name;
        this.password = password;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}

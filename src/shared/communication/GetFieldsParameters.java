/**
 * GetFieldsParameters.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetFieldsParameters.
 */
public class GetFieldsParameters {
    private String username;
    private String password;
    private int    projectID;

    /**
     * Instantiates a new gets the fields parameters.
     */
    public GetFieldsParameters() {

    }

    /**
     * Instantiates a new gets the fields parameters.
     *
     * @param name
     *            the name
     * @param password
     *            the password
     * @param projectID
     *            the project id
     */
    public GetFieldsParameters(String name, String password, int projectID) {
        this.username = name;
        this.password = password;
        this.projectID = projectID;
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
     * @param name
     *            the new name
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
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Sets the project id.
     *
     * @param projectID
     *            the new project id
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

}

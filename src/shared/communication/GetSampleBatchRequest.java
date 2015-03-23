/**
 * GetSampleBatchRequest.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleBatchRequest.
 */
public class GetSampleBatchRequest implements Request {

    private String username;

    private String password;

    private int    projectId;

    /**
     * Instantiates a new gets the sample image parameters.
     */
    public GetSampleBatchRequest() {

    }

    /**
     * Instantiates a new gets the sample image parameters.
     *
     * @param name the name
     * @param password the password
     * @param projectId the project id
     */
    public GetSampleBatchRequest(String name, String password, int projectId) {
        this.username = name;
        this.password = password;
        this.projectId = projectId;
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

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the new project id
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

}

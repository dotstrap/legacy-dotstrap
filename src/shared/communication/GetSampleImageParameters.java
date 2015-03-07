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
     * @param name
     *            the name
     * @param password
     *            the password
     * @param projectID
     *            the project id
     */
    public GetSampleImageParameters(String name, String password, int projectID) {
        this.name = name;
        this.password = password;
        this.projectID = projectID;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
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

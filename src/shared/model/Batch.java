package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Batch.
 */
public class Batch {

    /** The id. */
    private int    ID;

    /** The file path. */
    private String filePath;

    /** The project id. */
    private int    projectID;

    /** The state. */
    private int    state;

    /**
     * Instantiates a new batch.
     */
    public Batch() {

    }

    /**
     * Instantiates a new batch.
     *
     * @param fp
     *            the fp
     * @param pID
     *            the id
     * @param s
     *            the s
     */
    public Batch(String fp, int pID, int s) {
        filePath = fp;
        projectID = pID;
        state = s;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the id.
     *
     * @param i
     *            the new id
     */
    public void setID(int i) {
        ID = i;
    }

    /**
     * Gets the file path.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path.
     *
     * @param s
     *            the new file path
     */
    public void setFilePath(String s) {
        filePath = s;
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
     * @param i
     *            the new project id
     */
    public void setProjectID(int i) {
        projectID = i;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param i
     *            the new state
     */
    public void setState(int i) {
        state = i;
    }
}

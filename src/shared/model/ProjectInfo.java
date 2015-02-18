package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectInfo.
 */
public class ProjectInfo {

    /** The name. */
    private String name;

    /** The id. */
    private int    ID;

    /**
     * Instantiates a new project info.
     */
    public ProjectInfo() {

    }

    /**
     * Instantiates a new project info.
     *
     * @param s
     *            the s
     */
    public ProjectInfo(String s) {
        name = s;
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
     * @param s
     *            the new name
     */
    public void setName(String s) {
        name = s;
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
}

package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Field.
 */
public class Field {

    /** The title. */
    private String title;

    /** The id. */
    private int    ID;

    /** The help path. */
    private String helpPath;

    /** The known path. */
    private String knownPath;

    /** The number. */
    private int    number;

    /** The project id. */
    private int    projectID;

    /** The width. */
    private int    width;

    /** The x coord. */
    private int    xCoord;

    /**
     * Instantiates a new field.
     */
    public Field() {
        knownPath = "";
    }

    /**
     * Instantiates a new field.
     *
     * @param title
     *            the title
     * @param help
     *            the help
     * @param known
     *            the known
     * @param num
     *            the num
     * @param projID
     *            the proj id
     * @param width
     *            the width
     * @param x
     *            the x
     */
    public Field(String title, String help, String known, int num, int projID,
            int width, int x) {
        this.title = title;
        this.helpPath = help;
        this.knownPath = known;
        this.number = num;
        this.projectID = projID;
        this.width = width;
        this.xCoord = x;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param s
     *            the new title
     */
    public void setTitle(String s) {
        title = s;
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
     * Gets the help.
     *
     * @return the help
     */
    public String getHelp() {
        return helpPath;
    }

    /**
     * Sets the help.
     *
     * @param s
     *            the new help
     */
    public void setHelp(String s) {
        helpPath = s;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param i
     *            the new number
     */
    public void setNumber(int i) {
        number = i;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX() {
        return xCoord;
    }

    /**
     * Sets the x.
     *
     * @param i
     *            the new x
     */
    public void setX(int i) {
        xCoord = i;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param i
     *            the new width
     */
    public void setWidth(int i) {
        width = i;
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
     * Gets the known path.
     *
     * @return the known path
     */
    public String getKnownPath() {
        return knownPath;
    }

    /**
     * Sets the known path.
     *
     * @param knownPath
     *            the new known path
     */
    public void setKnownPath(String knownPath) {
        this.knownPath = knownPath;
    }

}

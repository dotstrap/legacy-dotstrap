/**
 * Field.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Field.
 */
public class Field {

    private String knownPath;

    private String helpPath;

    private int    ID;

    private int    number;

    private int    projectID;

    private String title;

    private int    width;

    private int    xCoord;

    /**
     * Instantiates a new Field.
     *
     * @param knownPath the known path
     * @param helpPath the help path
     * @param id the id
     * @param number the number
     * @param projectID the project id
     * @param title the title
     * @param width the width
     * @param xCoord the x coord
     */
    public Field(String helpPath, String knownPath, int id, int number,
            int projectID, String title, int width, int xCoord) {
        this.ID        = id;
        this.knownPath = knownPath;
        this.helpPath  = helpPath;
        this.number    = number;
        this.projectID = projectID;
        this.title     = title;
        this.width     = width;
        this.xCoord    = xCoord;
    }

    public String getKnownPath() {
        return this.knownPath;
    }

    public void setKnownPath(String knownPath) {
        this.knownPath = knownPath;
    }

    public String getHelpPath() {
        return this.helpPath;
    }

    public void setHelpPath(String helpPath) {
        this.helpPath = helpPath;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the x coord.
     *
     * @return the x coord
     */
    public int getxCoord() {
        return this.xCoord;
    }

    /**
     * Sets the x coord.
     *
     * @param xCoord the new x coord
     */
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

}

/**
 * Field.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Field.
 */
public class Field {
    private int    fieldID;
    private int    projectID;
    private String title;
    private String knownData;
    private String helpURL;
    private String fieldPath;
    private int    xCoord;
    private int    width;
    private int    colNum;

    /**
     * Instantiates a new Field.
     *
     */
    public Field() {
        this.fieldID   = -1;
        this.projectID = -1;
        this.title     = "title";
        this.knownData = "knownData";
        this.helpURL   = "helpURL";
        this.fieldPath = "fieldPath";
        this.xCoord    = -1;
        this.width     = -1;
        this.colNum    = -1;
    }

    /**
     * Instantiates a new Field.
     *
     * @param projectID
     * @param title
     * @param knownData
     * @param helpURL
     * @param fieldPath
     * @param xCoord
     * @param width
     * @param colNum
     */
    public Field(int projectID, String title, String knownData, String helpURL,
            String fieldPath, int xCoord, int width, int colNum) {
        this.projectID = projectID;
        this.title = title;
        this.knownData = knownData;
        this.helpURL = helpURL;
        this.fieldPath = fieldPath;
        this.xCoord = xCoord;
        this.width = width;
        this.colNum = colNum;
    }

    /**
     * Instantiates a new Field.
     *
     * @param fieldID
     * @param projectID
     * @param title
     * @param knownData
     * @param helpURL
     * @param fieldPath
     * @param xCoord
     * @param width
     * @param colNum
     */
    public Field(int fieldID, int projectID, String title, String knownData,
            String helpURL, String fieldPath, int xCoord, int width, int colNum) {
        this.fieldID = fieldID;
        this.projectID = projectID;
        this.title = title;
        this.knownData = knownData;
        this.helpURL = helpURL;
        this.fieldPath = fieldPath;
        this.xCoord = xCoord;
        this.width = width;
        this.colNum = colNum;
    }

    public int getFieldID() {
        return this.fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
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

    public String getKnownData() {
        return this.knownData;
    }

    public void setKnownData(String knownData) {
        this.knownData = knownData;
    }

    public String getHelpURL() {
        return this.helpURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public String getFieldPath() {
        return this.fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public int getxCoord() {
        return this.xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

}

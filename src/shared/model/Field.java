/**
 * Field.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Field.
 */
public class Field {
    private int    fieldId;
    private int    projectId;
    private String title;
    private String knownData;
    private String helpURL;
    private int    xCoord;
    private int    width;
    private int    colNum;

    /**
     * Instantiates a new Field.
     *
     */
    public Field() {
        this.fieldId   = -1;
        this.projectId = -1;
        this.title     = "title";
        this.knownData = "knownData";
        this.helpURL   = "helpURL";
        this.xCoord    = -1;
        this.width     = -1;
        this.colNum    = -1;
    }

    /**
     * Instantiates a new Field.
     *
     * @param projectId
     * @param title
     * @param knownData
     * @param helpURL
     * @param fieldPath
     * @param xCoord
     * @param width
     * @param colNum
     */
    public Field(int projectId, String title, String knownData, String helpURL,
             int xCoord, int width, int colNum) {
        this.projectId = projectId;
        this.title     = title;
        this.knownData = knownData;
        this.helpURL   = helpURL;
        this.xCoord    = xCoord;
        this.width     = width;
        this.colNum    = colNum;
    }

    /**
     * Instantiates a new Field.
     *
     * @param fieldId
     * @param projectId
     * @param title
     * @param knownData
     * @param helpURL
     * @param fieldPath
     * @param xCoord
     * @param width
     * @param colNum
     */
    public Field(int fieldId, int projectId, String title, String knownData,
            String helpURL, int xCoord, int width, int colNum) {
        this.fieldId   = fieldId;
        this.projectId = projectId;
        this.title     = title;
        this.knownData = knownData;
        this.helpURL   = helpURL;
        this.xCoord    = xCoord;
        this.width     = width;
        this.colNum    = colNum;
    }

    public int getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

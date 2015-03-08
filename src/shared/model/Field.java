/**
 * Field.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Field.
 */
public class Field {
    
    /** The help path. */
    private String helpPath;
    
    /** The id. */
    private int    ID;
    
    /** The known path. */
    private String knownPath;
    
    /** The number. */
    private int    number;
    
    /** The project id. */
    private int    projectID;
    
    /** The title. */
    private String title;
    
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
     * @param title the title
     * @param help the help
     * @param known the known
     * @param num the num
     * @param projID the proj id
     * @param width the width
     * @param x the x
     */
    public Field(String title, String help, String known, int num, int projID,
            int width, int x) {
        this.title = title;
        helpPath = help;
        knownPath = known;
        number = num;
        projectID = projID;
        this.width = width;
        xCoord = x;
    }
    
    public String getHelp() {
        return helpPath;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getKnownPath() {
        return knownPath;
    }
    
    public int getNumber() {
        return number;
    }
    
    public int getProjectID() {
        return projectID;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getX() {
        return xCoord;
    }
    
    public void setHelp(String s) {
        helpPath = s;
    }
    
    public void setID(int i) {
        ID = i;
    }
    
    public void setKnownPath(String knownPath) {
        this.knownPath = knownPath;
    }
    
    public void setNumber(int i) {
        number = i;
    }
    
    public void setProjectID(int i) {
        projectID = i;
    }
    
    public void setTitle(String s) {
        title = s;
    }
    
    public void setWidth(int i) {
        width = i;
    }
    
    public void setX(int i) {
        xCoord = i;
    }
    
}

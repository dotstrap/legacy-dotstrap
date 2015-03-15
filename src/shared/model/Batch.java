/**
 * Batch.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Batch A batch is considered an image.
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
     * Instantiates a new Batch without params.
     */
    public Batch() {
        this.filePath = null;
        this.ID = 0;
        this.projectID = 0;
        this.state = 0;
    }

    /**
     * Overload constructor instantiates a new Batch.
     *
     * @param filePath
     * @param projectID
     * @param state
     */
    public Batch(String filePath, int projectID, int state) {
        this.filePath = filePath;
        this.projectID = projectID;
        this.state = state;
    }

    /**
     * Overload constructor to instantiate a new Batch with params.
     *
     * @param filePath
     *            the file path
     * @param projectID
     *            the project id
     * @param state
     *            the state
     */
    public Batch(int id, String filePath, int projectID, int state) {
        this.filePath = filePath;
        this.ID = id;
        this.projectID = projectID;
        this.state = state;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

}

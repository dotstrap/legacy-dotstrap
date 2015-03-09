/**
 * Batch.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Batch
 * A batch is considered an image.
 */
public class Batch {

    private String filePath;
    private int    ID;
    private int    projectID;
    private int    state;

    /**
     * Instantiates a new Batch.
    =*
     * @param filePath
     * @param id
     * @param projectID
     * @param state
     */
    public Batch(String filePath, int id, int projectID, int state) {
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

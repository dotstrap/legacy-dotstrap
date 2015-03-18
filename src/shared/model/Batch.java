/**
 * Batch.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Batch (image).
 */
public class Batch {
    public final static int INCOMPLETE = 0;
    public final static int ACTIVE     = 1;
    public final static int COMPLETE   = 2;

    private int    batchID;
    private String filePath;
    private int    projectID;
    private int    status;
    private int    currUserID;

    /**
     * Instantiates a new Batch without params.
     */
    public Batch() {
        this.batchID    = -1;
        this.filePath   = "filePath";
        this.projectID  = -1;
        this.status     = INCOMPLETE;
        this.currUserID = -1;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param filePath
     * @param projectID
     * @param status
     */
    public Batch(String filePath, int projectID, int status) {
        this.batchID    = -1;
        this.filePath   = filePath;
        this.projectID  = projectID;
        this.status     = status;
        this.currUserID = -1;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param filePath
     * @param projectID
     * @param status
     * @param currUserID
     */
    public Batch(String filePath, int projectID, int status, int currUserID) {
        this.batchID    = -1;
        this.filePath   = filePath;
        this.projectID  = projectID;
        this.status     = status;
        this.currUserID = currUserID;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param batchID
     * @param filePath
     * @param projectID
     * @param status
     * @param currUserID
     */
    public Batch(int batchID, String filePath, int projectID, int status,
            int currUserID) {
        this.batchID    = batchID;
        this.filePath   = filePath;
        this.projectID  = projectID;
        this.status     = status;
        this.currUserID = currUserID;
    }

    public int getBatchID() {
        return this.batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrUserID() {
        return this.currUserID;
    }

    public void setCurrUserID(int currUserID) {
        this.currUserID = currUserID;
    }
}

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
 * The Class Batch A batch is considered an image.
 */
public class Batch {
    private int    batchID;
    private String filePath;
    private int    projectID;
    private int    status;

    /**
     * Instantiates a new Batch without params.
     */
    public Batch() {
        this.filePath  = null;
        this.batchID        = 0;
        this.projectID = 0;
        this.status    = 0;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param filePath
     * @param projectID
     * @param status
     */
    public Batch(String filePath, int projectID, int status) {
        this.filePath = filePath;
        this.projectID = projectID;
        this.status = status;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param batchID
     * @param filePath
     * @param projectID
     * @param status
     */
    public Batch(int batchID, String filePath, int projectID, int status) {
        this.batchID = batchID;
        this.filePath = filePath;
        this.projectID = projectID;
        this.status = status;
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
}


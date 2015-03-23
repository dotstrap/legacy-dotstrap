/**
 * Batch.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 23, 2015.
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

    private int    batchId;
    private String filePath;
    private int    projectId;
    private int    status;
    private int    currUserId;

    /**
     * Instantiates a new Batch without params.
     */
    public Batch() {
        this.batchId    = -1;
        this.filePath   = "filePath";
        this.projectId  = -1;
        this.status     = INCOMPLETE;
        this.currUserId = -1;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param filePath
     * @param projectId
     * @param status
     */
    public Batch(String filePath, int projectId, int status) {
        this.batchId    = -1;
        this.filePath   = filePath;
        this.projectId  = projectId;
        this.status     = status;
        this.currUserId = -1;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param filePath
     * @param projectId
     * @param status
     * @param currUserId
     */
    public Batch(String filePath, int projectId, int status, int currUserId) {
        this.batchId    = -1;
        this.filePath   = filePath;
        this.projectId  = projectId;
        this.status     = status;
        this.currUserId = currUserId;
    }

    /**
     * Instantiates a new Batch.
     *
     * @param batchId
     * @param filePath
     * @param projectId
     * @param status
     * @param currUserId
     */
    public Batch(int batchId, String filePath, int projectId, int status,
            int currUserId) {
        this.batchId    = batchId;
        this.filePath   = filePath;
        this.projectId  = projectId;
        this.status     = status;
        this.currUserId = currUserId;
    }

    public int getBatchId() {
        return this.batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrUserId() {
        return this.currUserId;
    }

    public void setCurrUserId(int currUserId) {
        this.currUserId = currUserId;
    }
}

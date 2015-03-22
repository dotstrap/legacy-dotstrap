/**
 * Project.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
public class Project {
    private int    projectID;
    private String title;
    private int    recordsPerBatch;
    private int    firstYCoord;
    private int    recordHeight;

    /**
     * Instantiates a new Project.
     */
    public Project() {
        this.title           = "";
        this.projectID       = 0;
        this.recordHeight    = 0;
        this.recordsPerBatch = 0;
        this.firstYCoord     = 0;
    }


    /**
     * Instantiates a new Project.
     *
     * @param title
     * @param recordsPerBatch
     * @param firstYCoord
     * @param recordHeight
     */
    public Project(String title, int recordsPerBatch, int firstYCoord,
            int recordHeight) {
        this.title           = title;
        this.recordsPerBatch = recordsPerBatch;
        this.firstYCoord     = firstYCoord;
        this.recordHeight    = recordHeight;
    }

    /**
     * Instantiates a new Project.
     *
     * @param id
     * @param title
     * @param recordsPerBatch
     * @param firstYCoord
     * @param recordHeight
     */
    public Project(int id, String title, int recordsPerBatch, int firstYCoord,
            int recordHeight) {
        this.projectID       = id;
        this.title           = title;
        this.recordsPerBatch = recordsPerBatch;
        this.firstYCoord     = firstYCoord;
        this.recordHeight    = recordHeight;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public void setProjectID(int id) {
        this.projectID = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRecordsPerBatch() {
        return this.recordsPerBatch;
    }

    public void setRecordsPerBatch(int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }

    public int getFirstYCoord() {
        return this.firstYCoord;
    }

    public void setFirstYCoord(int firstYCoord) {
        this.firstYCoord = firstYCoord;
    }

    public int getRecordHeight() {
        return this.recordHeight;
    }

    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }
}

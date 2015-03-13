/**
 * Project.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
public class Project {

    /** The project info. */
    private String name;
    
    /** The id. */
    private int    ID;

    /** The record height. */
    private int    recordHeight;
    
    /** The records per batch. */
    private int    recordsPerBatch;
    
    /** The first y. */
    private int    firstY;

    /**
     * Instantiates a new Project.
     * =*
     *
     * @param name the name
     * @param id the id
     * @param recordHeight the record height
     * @param recordsPerBatch the records per batch
     * @param firstY the first y
     */
    public Project(String name, int id, int recordHeight, int recordsPerBatch,
            int firstY) {
        this.name = name;
        this.ID = id;
        this.recordHeight = recordHeight;
        this.recordsPerBatch = recordsPerBatch;
        this.firstY = firstY;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getRecordHeight() {
        return this.recordHeight;
    }

    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }

    public int getRecordsPerBatch() {
        return this.recordsPerBatch;
    }

    public void setRecordsPerBatch(int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }

    public int getFirstY() {
        return this.firstY;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }
}

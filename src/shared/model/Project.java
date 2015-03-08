/**
 * Project.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
public class Project {
    
    /** The first y. */
    private int         firstY;
    
    /** The proj info. */
    private ProjectInfo projInfo;
    
    /** The record height. */
    private int         recordHeight;
    
    /** The records per batch. */
    private int         recordsPerBatch;
    
    /**
     * Instantiates a new project.
     */
    public Project() {
        
    }
    
    /**
     * Instantiates a new project.
     *
     * @param pi the pi
     * @param rpb the rpb
     * @param fY the f y
     * @param rH the r h
     */
    public Project(ProjectInfo pi, int rpb, int fY, int rH) {
        projInfo = pi;
        recordsPerBatch = rpb;
        firstY = fY;
        recordHeight = rH;
    }
    
    public int getFirstY() {
        return firstY;
    }
    
    public ProjectInfo getProjInfo() {
        return projInfo;
    }
    
    public int getRecordHeight() {
        return recordHeight;
    }
    
    public int getRecordsPerBatch() {
        return recordsPerBatch;
    }
    
    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }
    
    public void setProjInfo(ProjectInfo projInfo) {
        this.projInfo = projInfo;
    }
    
    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }
    
    public void setRecordsPerBatch(int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }
    
}

/**
 * Batch.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Batch.
 */
public class Batch {
    
    /** The file path. */
    private String filePath;
    
    /** The id. */
    private int    ID;
    
    /** The project id. */
    private int    projectID;
    
    /** The state. */
    private int    state;
    
    /**
     * Instantiates a new batch.
     */
    public Batch() {
        
    }
    
    /**
     * Instantiates a new batch.
     *
     * @param fp the fp
     * @param pID the id
     * @param s the s
     */
    public Batch(String fp, int pID, int s) {
        filePath = fp;
        projectID = pID;
        state = s;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public int getID() {
        return ID;
    }
    
    public int getProjectID() {
        return projectID;
    }
    
    public int getState() {
        return state;
    }
    
    public void setFilePath(String s) {
        filePath = s;
    }
    
    public void setID(int i) {
        ID = i;
    }
    
    public void setProjectID(int i) {
        projectID = i;
    }
    
    public void setState(int i) {
        state = i;
    }
}

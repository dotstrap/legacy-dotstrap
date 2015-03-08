/**
 * ProjectInfo.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectInfo.
 */
public class ProjectInfo {
    
    /** The id. */
    private int    ID;
    
    /** The name. */
    private String name;
    
    /**
     * Instantiates a new project info.
     */
    public ProjectInfo() {
        
    }
    
    /**
     * Instantiates a new project info.
     *
     * @param s the s
     */
    public ProjectInfo(String s) {
        name = s;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getName() {
        return name;
    }
    
    public void setID(int i) {
        ID = i;
    }
    
    public void setName(String s) {
        name = s;
    }
}

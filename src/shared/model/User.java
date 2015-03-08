/**
 * User.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
    
    /** The batch. */
    private int         batch;
    
    /** The creds. */
    private Credentials creds;
    
    /** The id. */
    private int         ID;
    
    /** The info. */
    private UserInfo    info;
    
    /** The record count. */
    private int         recordCount;
    
    /**
     * Instantiates a new user.
     */
    public User() {
        
    }
    
    /**
     * Instantiates a new user.
     *
     * @param c the c
     * @param ui the ui
     */
    public User(Credentials c, UserInfo ui) {
        creds = c;
        info = ui;
        recordCount = 0;
        batch = 0;
        ID = -1;
    }
    
    public Credentials getCreds() {
        return creds;
    }
    
    public int getCurrentBatch() {
        return batch;
    }
    
    public int getID() {
        return ID;
    }
    
    public int getRecordCount() {
        return recordCount;
    }
    
    public UserInfo getUserInfo() {
        return info;
    }
    
    public void setCreds(Credentials c) {
        creds = c;
    }
    
    public void setCurrentBatch(int i) {
        batch = i;
    }
    
    public void setID(int i) {
        ID = i;
    }
    
    public void setRecordCount(int i) {
        recordCount = i;
    }
    
    public void setUserInfo(UserInfo u) {
        info = u;
    }
}

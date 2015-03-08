/**
 * UserInfo.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInfo.
 */
public class UserInfo {
    
    /** The email. */
    private String email;
    
    /** The first. */
    private String first;
    
    /** The last. */
    private String last;
    
    /**
     * Instantiates a new user info.
     */
    public UserInfo() {
        
    }
    
    /**
     * Instantiates a new user info.
     *
     * @param f the f
     * @param l the l
     * @param e the e
     */
    public UserInfo(String f, String l, String e) {
        first = f;
        last = l;
        email = e;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
        return first;
    }
    
    public String getLastName() {
        return last;
    }
    
    public void setEmail(String s) {
        email = s;
    }
    
    public void setFirstName(String s) {
        first = s;
    }
    
    public void setLastName(String s) {
        last = s;
    }
}

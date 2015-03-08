/**
 * Credentials.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Credentials.
 */
public class Credentials {
    
    /** The password. */
    private String password;
    
    /** The username. */
    private String username;
    
    /**
     * Instantiates a new credentials.
     *
     * @param u the u
     * @param p the p
     */
    public Credentials(String u, String p) {
        username = u;
        password = p;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPassword(String s) {
        password = s;
    }
    
    public void setUsername(String s) {
        username = s;
    }
}

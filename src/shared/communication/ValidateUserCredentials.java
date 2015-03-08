/**
 * ValidateUserCredentials.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import shared.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserCredentials.
 */
public class ValidateUserCredentials {
    
    /** The password. */
    private String password;
    
    /** The username. */
    private String username;
    
    /**
     * Instantiates a new validate user credentials.
     *
     * @param u the u
     * @param p the p
     */
    public ValidateUserCredentials(String u, String p) {
        username = u;
        password = p;
    }
    
    public String getPassword() {
        return password;
    }
    
    public User getUser() {
        User user = new User();
        return user;
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

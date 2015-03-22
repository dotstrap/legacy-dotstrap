/**
 * ValidateUserParameters.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import shared.model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserParameters.
 */
public class ValidateUserParameters {

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Instantiates a new ValidateUserParameters.
     *
     */
    public ValidateUserParameters() {
        this.username = "";
        this.password = "";
    }

    /**
     * Instantiates a new validate user credentials.
     *
     * @param u the u
     * @param p the p
     */
    public ValidateUserParameters(String u, String p) {
        username = u;
        password = p;
    }

    /**
     * gets username.
     *
     * @return -> returns the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username.
     *
     * @param s            -> new username
     */
    public void setUsername(String s) {
        username = s;
    }

    /**
     * gets password.
     *
     * @return -> returns the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password.
     *
     * @param s            -> new password
     */
    public void setPassword(String s) {
        password = s;
    }

 }

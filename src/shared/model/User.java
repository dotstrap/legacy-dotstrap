/**
 * User.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {

    // Credentials
    private int    ID;
    private String username;
    private String password;
    // User info
    private String first;
    private String last;
    private String email;
    // Current project info
    private int    recordCount;
    private int    currBatch;

    /**
     * Instantiates a new User.
     */
    public User() {
        this.ID = -1;
        this.username = "username";
        this.password = "password";
        this.first = "first";
        this.last = "last";
        this.email = "email";
        this.recordCount = -1;
        this.currBatch = -1;
    }

    /**
     * Instantiates a new User.
     *
     * @param username
     * @param password
     * @param first
     * @param last
     * @param email
     * @param recordCount
     * @param currBatch
     */
    public User(String username, String password, String first, String last,
            String email, int recordCount, int currBatch) {
        this.username = username;
        this.password = password;
        this.first = first;
        this.last = last;
        this.email = email;
        this.recordCount = recordCount;
        this.currBatch = currBatch;
    }

    /**
     * Instantiates a new User.
     *
     * @param id
     * @param username
     * @param password
     * @param first
     * @param last
     * @param email
     * @param recordCount
     * @param currBatch
     */
    public User(int id, String username, String password, String first,
            String last, String email, int recordCount, int currBatch) {
        this.ID = id;
        this.username = username;
        this.password = password;
        this.first = first;
        this.last = last;
        this.email = email;
        this.recordCount = recordCount;
        this.currBatch = currBatch;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getCurrBatch() {
        return this.currBatch;
    }

    public void setCurrBatch(int currBatch) {
        this.currBatch = currBatch;
    }
}

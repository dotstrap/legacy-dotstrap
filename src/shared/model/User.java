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

    // Credentials
    private String password;
    private String username;
    // User information
    private String email;
    private String first;
    private String last;
    // Current project information
    private int    batch;
    private int    ID;
    private int    recordCount;

    /**
     * @param password
     * @param username
     * @param email
     * @param first
     * @param last
     * @param batch
     * @param iD
     * @param recordCount
     */
    public User(String password, String username, String email, String first,
            String last, int batch, int iD, int recordCount) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.first = first;
        this.last = last;
        this.batch = batch;
        this.ID = iD;
        this.recordCount = recordCount;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getBatch() {
        return this.batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

}

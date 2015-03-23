/**
 * User.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */

package shared.model;

/**
 * The Class User.
 */
public class User {
    // Credentials
    private int    userId;
    private String username;
    private String password;
    // User info
    private String first;
    private String last;
    private String email;
    // Current project info
    private int    recordCount;
    private int    currentBatchId;

    //@formatter:off
    /**
     * Instantiates a new User.
     */
    public User() {
        this.userId         = -1;
        this.username       = "username";
        this.password       = "password";
        this.first          = "first";
        this.last           = "last";
        this.email          = "email";
        this.recordCount    = -1;
        this.currentBatchId = -1;
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
    public User(String username, String password, String first, String last, String email,
            int recordCount, int currBatch) {
        this.username       = username;
        this.password       = password;
        this.first          = first;
        this.last           = last;
        this.email          = email;
        this.recordCount    = recordCount;
        this.currentBatchId = currBatch;
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
        this.userId         = id;
        this.username       = username;
        this.password       = password;
        this.first          = first;
        this.last           = last;
        this.email          = email;
        this.recordCount    = recordCount;
        this.currentBatchId = currBatch;
    }
    //@formatter:on

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int id) {
        this.userId = id;
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
        return this.currentBatchId;
    }

    public void setCurrBatch(int currBatch) {
        this.currentBatchId = currBatch;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        if (o == this)
            return true;

        User other = (User) o;
// @formatter:off
        return (userId == other.getUserId()
                && username.equals(other.getUsername())
                && password.equals(other.getPassword())
                && first.equals(other.getFirst())
                && last.equals(other.getLast())
                && email.equals(other.getEmail()));
        // @formatter:on
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("User ID: " + userId + "\n");
        output.append("Username: " + username + "\n");
        output.append("Password: " + password + "\n");
        output.append("First Name: " + first + "\n");
        output.append("Last Name: " + last + "\n");
        output.append("Email: " + email + "\n");
        output.append("Indexed Records: " + recordCount + "\n");
        output.append("Current Image ID: " + currentBatchId + "\n");

        return output.toString();
    }

}

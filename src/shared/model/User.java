/**
 * User.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class User.
 */
public class User {
  // Credentials
  private int userId;
  private String username;
  private String password;

  // User info
  private String first;
  private String last;
  private String email;

  // Current project info
  private int recordCount;
  private int currentBatchId;

  /**
   * Instantiates a new user.
   */
  public User() {// @formatter:off
    userId = -1;
    username = "username";
    password = "password";
    first = "first";
    last = "last";
    email = "email";
    recordCount = -1;
    currentBatchId = -1;
  }

  /**
   * Instantiates a new user.
   *
   * @param username the username
   * @param password the password
   * @param first the first
   * @param last the last
   * @param email the email
   * @param recordCount the record count
   * @param currBatch the curr batch
   */
  public User(String username, String password, String first, String last,
      String email, int recordCount, int currBatch) {
    this.username = username;
    this.password = password;
    this.first = first;
    this.last = last;
    this.email = email;
    this.recordCount = recordCount;
    currentBatchId = currBatch;
  }// @formatter:on

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    if (o == this) {
      return true;
    }

    User other = (User) o; // @formatter:off
    return ((userId == other.getUserId())
        && username.equals(other.getUsername())
        && password.equals(other.getPassword())
        && first.equals(other.getFirst())
        && last.equals(other.getLast())
        && email.equals(other.getEmail())); // @formatter:on
  }

  public int getCurrBatch() {
    return currentBatchId;
  }

  public String getEmail() {
    return email;
  }

  public String getFirst() {
    return first;
  }

  public String getLast() {
    return last;
  }

  public String getPassword() {
    return password;
  }

  public int getRecordCount() {
    return recordCount;
  }

  public int getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public void setCurrBatch(int currBatch) {
    currentBatchId = currBatch;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public void setLast(String last) {
    this.last = last;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }

  public void setUserId(int id) {
    userId = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("User [userId=");
    sb.append(userId);
    sb.append(", username=");
    sb.append(username);
    sb.append(", password=");
    sb.append(password);
    sb.append(", first=");
    sb.append(first);
    sb.append(", last=");
    sb.append(last);
    sb.append(", email=");
    sb.append(email);
    sb.append(", recordCount=");
    sb.append(recordCount);
    sb.append(", currentBatchId=");
    sb.append(currentBatchId);
    sb.append("]");
    return sb.toString();
  }

}

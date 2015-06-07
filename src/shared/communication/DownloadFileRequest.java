/**
 * DownloadFileRequest.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class DownloadFileRequest.
 */
public class DownloadFileRequest implements Request {
  private String url;
  private String username;
  private String password;

  /**
   * Instantiates a new DownloadFileRequest.
   *
   */
  public DownloadFileRequest() {}

  /**
   * Instantiates a new DownloadFileRequest.
   *
   * @param url the url
   */
  public DownloadFileRequest(String url) {
    this.url = url;
  }

  /**
   * Instantiates a new DownloadFileRequest.
   *
   * @param url the url
   * @param username the username
   * @param password the password
   */
  public DownloadFileRequest(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return this.username + "\n" + this.password + "\n" + this.url + "\n";
  }

}

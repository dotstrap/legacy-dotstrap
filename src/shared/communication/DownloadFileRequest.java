/**
 * DownloadFileRequest.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
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
   * Instantiates a new download file request.
   */
  public DownloadFileRequest() {}

  /**
   * Instantiates a new download file request.
   *
   * @param url the url
   */
  public DownloadFileRequest(String url) {
    this.url = url;
  }

  /**
   * Instantiates a new download file request.
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

  public String getPassword() {
    return password;
  }

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUrl(String url) {
    this.url = url;
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
    return username + "\n" + password + "\n" + url + "\n";
  }

}

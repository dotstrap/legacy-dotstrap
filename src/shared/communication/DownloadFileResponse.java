/**
 * DownloadFileResponse.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

/**
 * The Class DownloadFileResponse.
 */
public class DownloadFileResponse implements Response {

  private byte[] fileBytes;

  /**
   * Instantiates a new download file response.
   *
   * @param fileBytes the file bytes
   */
  public DownloadFileResponse(byte[] fileBytes) {
    this.fileBytes = fileBytes;
  }

  public byte[] getFileBytes() {
    return fileBytes;
  }

  public void setFileBytes(byte[] fileBytes) {
    this.fileBytes = fileBytes;
  }
}

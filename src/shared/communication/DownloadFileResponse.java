/**
 * DownloadFileResponse.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadFileResponse.
 */
public class DownloadFileResponse implements Response {

  /** The file bytes. */
  private byte[] fileBytes;

  /**
   * Bytes returned by file referenced by URL.
   *
   * @param fileBytes the file bytes
   */
  public DownloadFileResponse(byte[] fileBytes) {
    this.fileBytes = fileBytes;
  }

  /**
   * Gets the file bytes.
   *
   * @return the fileBytes
   */
  public byte[] getFileBytes() {
    return fileBytes;
  }

  /**
   * Sets the file bytes.
   *
   * @param fileBytes the fileBytes to set
   */
  public void setFileBytes(byte[] fileBytes) {
    this.fileBytes = fileBytes;
  }
}

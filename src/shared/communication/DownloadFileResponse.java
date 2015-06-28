package shared.communication;

public class DownloadFileResponse implements Response {

  private byte[] fileBytes;

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

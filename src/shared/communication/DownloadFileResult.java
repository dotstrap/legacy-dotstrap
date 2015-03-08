/**
 * DownloadFileResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadFileResult.
 */
public class DownloadFileResult {
    
    /** The file bytes. */
    private byte[] fileBytes;
    
    /**
     * Instantiates a new download file result.
     *
     * @param fileBytes the file bytes
     */
    public DownloadFileResult(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    
    public byte[] getFileBytes() {
        return fileBytes;
    }
    
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}

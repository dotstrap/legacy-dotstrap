/**
 * DownloadFileParameters.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadFileParameters.
 */
public class DownloadFileParameters {
    
    /** The url. */
    private String url;
    
    /**
     * Instantiates a new download file parameters.
     *
     * @param url the url
     */
    public DownloadFileParameters(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}

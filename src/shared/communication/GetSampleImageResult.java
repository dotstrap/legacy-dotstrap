/**
 * GetSampleImageResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleImageResult.
 */
public class GetSampleImageResult {
    
    /** The link. */
    private String  link;
    
    /** The url. */
    private URL     url;
    
    /** The valid user. */
    private boolean validUser;
    
    /**
     * Instantiates a new gets the sample image result.
     */
    public GetSampleImageResult() {
        validUser = false;
    }
    
    public String getLink() {
        return link;
    }
    
    public URL getURL() {
        return url;
    }
    
    /**
     * Checks if is valid user.
     */
    public boolean isValidUser() {
        return validUser;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public void setURL(URL url) {
        this.url = url;
    }
    
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }
    
    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (validUser) {
            sb.append(url + "\n");
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}

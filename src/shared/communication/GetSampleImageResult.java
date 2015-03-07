package shared.communication;

import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleImageResult.
 */
public class GetSampleImageResult {

    /** The url. */
    private URL    url;

    /** The link. */
    private String link;

    /**
     * Gets the link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link.
     *
     * @param link
     *            the new link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /** The valid user. */
    private boolean validUser;

    /**
     * Instantiates a new gets the sample image result.
     */
    public GetSampleImageResult() {
        validUser = false;
    }

    /**
     * Sets the url.
     *
     * @param url
     *            the new url
     */
    public void setURL(URL url) {
        this.url = url;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public URL getURL() {
        return url;
    }

    /**
     * Checks if is valid user.
     *
     * @return true, if is valid user
     */
    public boolean isValidUser() {
        return validUser;
    }

    /**
     * Sets the valid user.
     *
     * @param validUser
     *            the new valid user
     */
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    /**
     * 
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
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

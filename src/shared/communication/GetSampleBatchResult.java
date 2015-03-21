package shared.communication;

import java.net.URL;

import shared.model.Batch;

// TODO: Auto-generated Javadoc
/**
 * The Class GetSampleBatchResult.
 */
public class GetSampleBatchResult {
    private URL url;
    private String link;
    private Batch sampleBatch;
    private boolean validUser;

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

    /**
     * Instantiates a new gets the sample image result.
     */
    public GetSampleBatchResult() {
        validUser = false;
    }

    /**
     * Sets the url.
     *
     * @param url
     *            the new url
     */
    public void setUrl(URL url) {
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
     * @return the sampleBatch
     */
    public Batch getSampleBatch() {
        return sampleBatch;
    }

    /**
     * @param sampleBatch
     *            the sampleBatch to set
     */
    public void setSampleBatch(Batch sampleBatch) {
        this.sampleBatch = sampleBatch;
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
        sb.append(url + "\n");
        return sb.toString();
    }
}

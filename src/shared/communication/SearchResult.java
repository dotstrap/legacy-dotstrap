package shared.communication;

import java.net.URL;
import java.util.ArrayList;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchResult.
 */
public class SearchResult {
    
    /** The records. */
    private ArrayList<Record> records;
    
    /** The urls. */
    private ArrayList<URL>    urls;
    
    /** The links. */
    private ArrayList<String> links;
    
    /** The valid user. */
    private boolean           validUser;

    /**
     * Instantiates a new search result.
     */
    public SearchResult() {
        validUser = false;
    }

    /**
     * Gets the records.
     *
     * @return the records
     */
    public ArrayList<Record> getRecords() {
        return records;
    }

    /**
     * Sets the records.
     *
     * @param records the new records
     */
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    /**
     * Gets the urls.
     *
     * @return the urls
     */
    public ArrayList<URL> getUrls() {
        return urls;
    }

    /**
     * Sets the urls.
     *
     * @param urls the new urls
     */
    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
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
     * @param validUser the new valid user
     */
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    public ArrayList<String> getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    /** 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (validUser & records.size() > 0) {
            for (int i = 0; i < records.size(); ++i) {
                sb.append(records.get(i).getBatchID() + "\n");
                sb.append(urls.get(i) + "\n");
                sb.append(records.get(i).getRecordNumber() + "\n");
                sb.append(records.get(i).getFieldID() + "\n");
            }
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }

}

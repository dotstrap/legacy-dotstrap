/**
 * SearchResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.net.URL;
import java.util.ArrayList;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchResult.
 */
public class SearchResult {
    
    /** The links. */
    private ArrayList<String> links;
    
    /** The records. */
    private ArrayList<Record> records;
    
    /** The urls. */
    private ArrayList<URL>    urls;
    
    /** The valid user. */
    private boolean           validUser;
    
    /**
     * Instantiates a new search result.
     */
    public SearchResult() {
        validUser = false;
    }
    
    public ArrayList<String> getLinks() {
        return links;
    }
    
    public ArrayList<Record> getRecords() {
        return records;
    }
    
    public ArrayList<URL> getUrls() {
        return urls;
    }
    
    /**
     * Checks if is valid user.
     */
    public boolean isValidUser() {
        return validUser;
    }
    
    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }
    
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
    
    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
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
        if (validUser & (records.size() > 0)) {
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

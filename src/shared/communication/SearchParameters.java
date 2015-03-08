/**
 * SearchParameters.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchParameters.
 */
public class SearchParameters {
    
    /** The field id. */
    private ArrayList<Integer> fieldID;
    
    /** The name. */
    private String             name;
    
    /** The password. */
    private String             password;
    
    /** The search. */
    private ArrayList<String>  search;
    
    /**
     * Instantiates a new search parameters.
     */
    public SearchParameters() {
        
    }
    
    /**
     * Instantiates a new search parameters.
     *
     * @param name the name
     * @param password the password
     * @param fieldID the field id
     * @param search the search
     */
    public SearchParameters(String name, String password,
            ArrayList<Integer> fieldID, ArrayList<String> search) {
        this.name = name;
        this.password = password;
        this.fieldID = fieldID;
        this.search = search;
    }
    
    public ArrayList<Integer> getFieldID() {
        return fieldID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public ArrayList<String> getSearch() {
        return search;
    }
    
    public void setFieldID(ArrayList<Integer> fieldID) {
        this.fieldID = fieldID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setSearch(ArrayList<String> search) {
        this.search = search;
    }
    
}

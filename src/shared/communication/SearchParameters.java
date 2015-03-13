package shared.communication;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchParameters.
 */
public class SearchParameters {
    
    /** The name. */
    private String             name;
    
    /** The password. */
    private String             password;
    
    /** The field id. */
    private ArrayList<Integer> fieldID;
    
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

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the field id.
     *
     * @return the field id
     */
    public ArrayList<Integer> getFieldID() {
        return fieldID;
    }

    /**
     * Sets the field id.
     *
     * @param fieldID the new field id
     */
    public void setFieldID(ArrayList<Integer> fieldID) {
        this.fieldID = fieldID;
    }

    /**
     * Gets the search.
     *
     * @return the search
     */
    public ArrayList<String> getSearch() {
        return search;
    }

    /**
     * Sets the search.
     *
     * @param search the new search
     */
    public void setSearch(ArrayList<String> search) {
        this.search = search;
    }

}

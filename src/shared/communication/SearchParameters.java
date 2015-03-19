package shared.communication;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchParameters.
 */
public class SearchParameters {
    private String username;
    private String password;
    private List<Integer> fieldIds;
    private ArrayList<String> searchQueries;

    /**
     * Instantiates a new search parameters.
     */
    public SearchParameters() {

    }

    /**
     * Instantiates a new search parameters.
     *
     * @param name
     *            the name
     * @param password
     *            the password
     * @param fieldID
     *            the field id
     * @param search
     *            the search
     */
    public SearchParameters(String name, String password,
            ArrayList<Integer> fieldID, ArrayList<String> search) {
        this.username = name;
        this.password = password;
        this.searchQueries = search;
    }

    /**
     * @param name
     * @param password
     * @param fieldIds
     * @param searchQuery
     */
    public SearchParameters(String name, String password,
            List<Integer> fieldIds, ArrayList<String> searchQuery) {
        this.username = name;
        this.password = password;
        this.fieldIds = fieldIds;
        this.searchQueries = searchQuery;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setUsername(String name) {
        this.username = name;
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
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fieldIds
     */
    public List<Integer> getFieldIds() {
        return fieldIds;
    }

    /**
     * @param fieldIds the fieldIds to set
     */
    public void setFieldIds(List<Integer> fieldIds) {
        this.fieldIds = fieldIds;
    }

    /**
     * Gets the search.
     *
     * @return the search
     */
    public ArrayList<String> getSearchQueries() {
        return searchQueries;
    }

    /**
     * Sets the search.
     *
     * @param search
     *            the new search
     */
    public void setSearchQueries(ArrayList<String> search) {
        this.searchQueries = search;
    }

}

package shared.communication;

import java.util.List;

import shared.model.Record;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchParameters.
 */
public class SubmitBatchParameters {
    private String       username;
    private String       password;
    private int          batchID;
    private List<Record> fieldValues;

    /**
     * @param username
     * @param password
     * @param batchID
     * @param fieldValues
     */
    public SubmitBatchParameters(String username, String password, int batchID,
            List<Record> fieldValues) {
        this.username    = username;
        this.password    = password;
        this.batchID     = batchID;
        this.fieldValues = fieldValues;
    }

    /**
     * Instantiates a new submit batch parameters.
     */
    public SubmitBatchParameters() {

    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the batchID
     */
    public int getBatchID() {
        return batchID;
    }

    /**
     * @param batchID the batchID to set
     */
    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    /**
     * @return the fieldValues
     */
    public List<Record> getFieldValues() {
        return fieldValues;
    }

    /**
     * @param fieldValues the fieldValues to set
     */
    public void setFieldValues(List<Record> fieldValues) {
        this.fieldValues = fieldValues;
    }

}

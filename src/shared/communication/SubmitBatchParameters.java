package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchParameters.
 */
public class SubmitBatchParameters {

    /** The name. */
    private String name;

    /** The password. */
    private String password;

    /** The batch id. */
    private int    batchID;

    /** The field values. */
    private String fieldValues;

    /**
     * Instantiates a new submit batch parameters.
     */
    public SubmitBatchParameters() {

    }

    /**
     * Instantiates a new submit batch parameters.
     *
     * @param name
     *            the name
     * @param password
     *            the password
     * @param batchID
     *            the batch id
     * @param fieldValues
     *            the field values
     */
    public SubmitBatchParameters(String name, String password, int batchID,
            String fieldValues) {
        this.name = name;
        this.password = password;
        this.batchID = batchID;
        this.fieldValues = fieldValues;
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
     * @param name
     *            the new name
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
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the batch id.
     *
     * @return the batch id
     */
    public int getBatchID() {
        return batchID;
    }

    /**
     * Sets the batch id.
     *
     * @param batchID
     *            the new batch id
     */
    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    /**
     * Gets the field values.
     *
     * @return the field values
     */
    public String getFieldValues() {
        return fieldValues;
    }

    /**
     * Sets the field values.
     *
     * @param fieldValues
     *            the new field values
     */
    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }
}

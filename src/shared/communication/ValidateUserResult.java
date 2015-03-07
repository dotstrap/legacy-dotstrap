package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserResult.
 */
public class ValidateUserResult {

    /** The fn. */
    private String  fn;

    /** The ln. */
    private String  ln;

    /** The validate. */
    private boolean validate;

    /** The batch. */
    private boolean batch;

    /** The records. */
    private int     records;

    /**
     * Instantiates a new validate user result.
     */
    public ValidateUserResult() {
        validate = false;
        batch = false;
    }

    /**
     * Instantiates a new validate user result.
     *
     * @param val
     *            the val
     * @param first
     *            the first
     * @param last
     *            the last
     * @param number
     *            the number
     */
    public ValidateUserResult(boolean val, String first, String last, int number) {
        validate = val;
        fn = first;
        ln = last;
        records = number;
    }

    /**
     * gets output.
     *
     * @return -> bool validate
     */
    public boolean isOutput() {
        return validate;
    }

    /**
     * sets output.
     *
     * @param o
     *            -> new output
     */
    public void setOutput(boolean o) {
        validate = o;
    }

    /**
     * gets the firstname.
     *
     * @return -> firstname
     */
    public String getFirstName() {
        return fn;
    }

    /**
     * gets the lastname.
     *
     * @return -> lastname
     */
    public String getLastName() {
        return ln;
    }

    /**
     * sets the firstname.
     *
     * @param s
     *            -> new firstname
     */
    public void setFirstName(String s) {
        fn = s;
    }

    /**
     * sets the lastname.
     *
     * @param s
     *            -> new lastname
     */
    public void setLastName(String s) {
        ln = s;
    }

    /**
     * gets the record number.
     *
     * @return -> record number
     */
    public int getRecordNum() {
        return records;
    }

    /**
     * sets the record number.
     *
     * @param i
     *            -> new record number
     */
    public void setRecordNum(int i) {
        records = i;
    }

    /**
     * Checks if is batch.
     *
     * @return true, if is batch
     */
    public boolean isBatch() {
        return batch;
    }

    /**
     * Sets the batch.
     *
     * @param batch
     *            the new batch
     */
    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    /**
     * 
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (this.validate) {
            output.append("TRUE\n");
            output.append(this.fn + "\n");
            output.append(this.ln + "\n");
            output.append(this.records + "\n");
        } else {
            output.append("FALSE\n");
        }
        return output.toString();
    }
}

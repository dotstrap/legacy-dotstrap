/**
 * ValidateUserResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidateUserResult.
 */
public class ValidateUserResult {
    
    /** The batch. */
    private boolean batch;
    
    /** The fn. */
    private String  fn;
    
    /** The ln. */
    private String  ln;
    
    /** The records. */
    private int     records;
    
    /** The validate. */
    private boolean validate;
    
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
     * @param val the val
     * @param first the first
     * @param last the last
     * @param number the number
     */
    public ValidateUserResult(boolean val, String first, String last, int number) {
        validate = val;
        fn = first;
        ln = last;
        records = number;
    }
    
    public String getFirstName() {
        return fn;
    }
    
    public String getLastName() {
        return ln;
    }
    
    public int getRecordNum() {
        return records;
    }
    
    /**
     * Checks if is batch.
     */
    public boolean isBatch() {
        return batch;
    }
    
    /**
     * Checks if is output.
     */
    public boolean isOutput() {
        return validate;
    }
    
    public void setBatch(boolean batch) {
        this.batch = batch;
    }
    
    public void setFirstName(String s) {
        fn = s;
    }
    
    public void setLastName(String s) {
        ln = s;
    }
    
    public void setOutput(boolean o) {
        validate = o;
    }
    
    public void setRecordNum(int i) {
        records = i;
    }
    
    /**
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (validate) {
            output.append("TRUE\n");
            output.append(fn + "\n");
            output.append(ln + "\n");
            output.append(records + "\n");
        } else {
            output.append("FALSE\n");
        }
        return output.toString();
    }
}

/**
 * SubmitBatchParameters.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchParameters.
 */
public class SubmitBatchParameters {
    
    /** The batch id. */
    private int    batchID;
    
    /** The field values. */
    private String fieldValues;
    
    /** The name. */
    private String name;
    
    /** The password. */
    private String password;
    
    /**
     * Instantiates a new submit batch parameters.
     */
    public SubmitBatchParameters() {
        
    }
    
    /**
     * Instantiates a new submit batch parameters.
     *
     * @param name the name
     * @param password the password
     * @param batchID the batch id
     * @param fieldValues the field values
     */
    public SubmitBatchParameters(String name, String password, int batchID,
            String fieldValues) {
        this.name = name;
        this.password = password;
        this.batchID = batchID;
        this.fieldValues = fieldValues;
    }
    
    public int getBatchID() {
        return batchID;
    }
    
    public String getFieldValues() {
        return fieldValues;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }
    
    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

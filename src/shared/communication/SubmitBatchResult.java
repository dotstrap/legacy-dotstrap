/**
 * SubmitBatchResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 8, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

// TODO: Auto-generated Javadoc
/**
 * The Class SubmitBatchResult.
 */
public class SubmitBatchResult {
    
    /** The success. */
    private boolean success;
    
    /**
     * Instantiates a new submit batch result.
     */
    public SubmitBatchResult() {
        success = false;
    }
    
    /**
     * Checks if is success.
     */
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * (non-Javadoc).
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        if (success) {
            sb.append("TRUE\n");
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}

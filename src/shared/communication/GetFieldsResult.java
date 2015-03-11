/**
 * GetFieldsResult.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 10, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class GetFieldsResult.
 */
public class GetFieldsResult {
    
    /** The fields. */
    private ArrayList<Field> fields;
    
    /** The valid user. */
    private boolean          validUser;
    
    /**
     * Instantiates a new gets the fields result.
     */
    public GetFieldsResult() {
        validUser = false;
    }
    
    public ArrayList<Field> getFields() {
        return fields;
    }
    
    /**
     * Checks if is valid user.
     */
    public boolean isValidUser() {
        return validUser;
    }
    
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
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
        if (validUser) {
            for (Field f : fields) {
                sb.append(f.getProjectID() + "\n");
                sb.append(f.getID() + "\n");
                sb.append(f.getTitle() + "\n");
            }
        } else {
            sb.append("FAILED\n");
        }
        return sb.toString();
    }
}

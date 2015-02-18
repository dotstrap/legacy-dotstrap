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

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     *
     * @param fields the new fields
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    /**
     * Checks if is valid user.
     *
     * @return true, if is valid user
     */
    public boolean isValidUser() {
        return validUser;
    }

    /**
     * Sets the valid user.
     *
     * @param validUser the new valid user
     */
    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    /** 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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

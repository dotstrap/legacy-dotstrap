/**
 * GetFieldsResponse.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.communication;

import java.util.List;

import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class GetFieldsResponse.
 */
public class GetFieldsResponse {
    private List<Field> fields;

    /**
     * Instantiates a new gets the fields result.
     */
    public GetFieldsResponse() {
        this.fields = null;
    }

    /**
     * @param fields
     */
    public GetFieldsResponse(List<Field> fields) {
        this.fields = fields;
    }

    /**
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    /**
     *
     * (non-Javadoc).
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            sb.append(f.getProjectId() + "\n");
            sb.append(f.getFieldId() + "\n");
            sb.append(f.getTitle() + "\n");
        }
        return sb.toString();
    }
}

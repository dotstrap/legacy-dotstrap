package shared.communication;

import java.util.List;

import shared.model.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class GetFieldsResult.
 */
public class GetFieldsResult {
    private List<Field> fields;

    /**
     * Instantiates a new gets the fields result.
     */
    public GetFieldsResult() {
        this.fields = null;
    }

    /**
     * @param fields
     */
    public GetFieldsResult(List<Field> fields) {
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
            sb.append(f.getProjectID() + "\n");
            sb.append(f.getFieldID() + "\n");
            sb.append(f.getTitle() + "\n");
        }
        return sb.toString();
    }
}

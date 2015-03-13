package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Record.
 */
public class Record {
    
    /** The id. */
    private int    ID;
    
    /** The Rec num. */
    private int    RecNum;
    
    /** The batch id. */
    private int    batchID;
    
    /** The data. */
    private String data;
    
    /** The field id. */
    private int    fieldID;

    /**
     * Instantiates a new record.
     */
    public Record() {

    }

    /**
     * Instantiates a new record.
     *
     * @param rn the rn
     * @param bID the b id
     * @param d the d
     * @param fID the f id
     */
    public Record(int rn, int bID, String d, int fID) {
        RecNum = rn;
        batchID = bID;
        data = d;
        fieldID = fID;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the id.
     *
     * @param i the new id
     */
    public void setID(int i) {
        ID = i;
    }

    /**
     * Gets the record number.
     *
     * @return the record number
     */
    public int getRecordNumber() {
        return RecNum;
    }

    /**
     * Sets the record number.
     *
     * @param i the new record number
     */
    public void setRecordNumber(int i) {
        RecNum = i;
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
     * @param i the new batch id
     */
    public void setBatchID(int i) {
        batchID = i;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets the field id.
     *
     * @return the field id
     */
    public int getFieldID() {
        return fieldID;
    }

    /**
     * Sets the field id.
     *
     * @param fieldID the new field id
     */
    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }
}

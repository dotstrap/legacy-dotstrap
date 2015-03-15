/**
 * Record.java
 * JRE v1.7.0_76
 * 
 * Created by William Myers on Mar 14, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Record.
 */
public class Record {

    /** The batch id. */
    private int    batchID;

    /** The data. */
    private String data;

    /** The field id. */
    private int    fieldID;

    /** The id. */
    private int    ID;

    /** The Rec num. */
    private int    RecNum;

    /**
     * Instantiates a new record.
     */
    public Record() {

    }

    /**
     * Instantiates a new record.
     *
     * @param rn
     *            the rn
     * @param bID
     *            the b id
     * @param d
     *            the d
     * @param fID
     *            the f id
     */
    public Record(int rn, int bID, String d, int fID) {
        RecNum = rn;
        batchID = bID;
        data = d;
        fieldID = fID;
    }

    public int getBatchID() {
        return batchID;
    }

    public String getData() {
        return data;
    }

    public int getFieldID() {
        return fieldID;
    }

    public int getID() {
        return ID;
    }

    public int getRecordNumber() {
        return RecNum;
    }

    public void setBatchID(int i) {
        batchID = i;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public void setID(int i) {
        ID = i;
    }

    public void setRecordNumber(int i) {
        RecNum = i;
    }
}

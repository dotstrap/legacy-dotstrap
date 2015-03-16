/**
 * Record.java
 * JRE v1.7.0_76
 *
 * Created by William Myers on Mar 15, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Record.
 */
public class Record {
    private int    ID;
    private int    fieldID;
    private int    batchID;
    private String batchURL;
    private String data;
    private int    RowNum;
    private int    ColNum;

    /**
     * Instantiates a new Record.
     */
    public Record() {
        ID       = -1;
        fieldID  = -1;
        batchID  = -1;
        batchURL = "batchURL";
        data     = "data";
        RowNum   = -1;
        ColNum   = -1;
    }

    /**
     * Instantiates a new Record.
     *
     * @param fieldID
     * @param batchID
     * @param batchURL
     * @param data
     * @param rowNum
     * @param colNum
     */
    public Record(int fieldID, int batchID, String batchURL, String data,
            int rowNum, int colNum) {
        this.fieldID  = fieldID;
        this.batchID  = batchID;
        this.batchURL = batchURL;
        this.data     = data;
        this.RowNum   = rowNum;
        this.ColNum   = colNum;
    }

    /**
     * Instantiates a new Record.
     *
     * @param id
     * @param fieldID
     * @param batchID
     * @param batchURL
     * @param data
     * @param rowNum
     * @param colNum
     */
    public Record(int id, int fieldID, int batchID, String batchURL, String data,
            int rowNum, int colNum) {
        this.ID       = id;
        this.fieldID  = fieldID;
        this.batchID  = batchID;
        this.batchURL = batchURL;
        this.data     = data;
        this.RowNum   = rowNum;
        this.ColNum   = colNum;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getFieldID() {
        return this.fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getBatchID() {
        return this.batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public String getBatchURL() {
        return this.batchURL;
    }

    public void setBatchURL(String batchURL) {
        this.batchURL = batchURL;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRowNum() {
        return this.RowNum;
    }

    public void setRowNum(int rowNum) {
        this.RowNum = rowNum;
    }

    public int getColNum() {
        return this.ColNum;
    }

    public void setColNum(int colNum) {
        this.ColNum = colNum;
    }
}

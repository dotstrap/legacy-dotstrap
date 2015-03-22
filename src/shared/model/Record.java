/**
 * Record.java
 * JRE v1.8.0_40
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Record.
 */
public class Record {
    private int    recordId;
    private int    fieldId;
    private int    batchId;
    private String batchURL;
    private String data;
    private int    RowNum;
    private int    ColNum;

    //@formatter:off
    /**
     * Instantiates a new Record.
     */
    public Record() { 
        recordId = -1;
        fieldId  = -1;
        batchId  = -1;
        batchURL = "batchURL"; 
        data     = "data";
        RowNum   = -1;
        ColNum   = -1;
    }

    /**
     * Instantiates a new Record.
     *
     * @param fieldId
     * @param batchId
     * @param batchURL
     * @param data
     * @param rowNum
     * @param colNum
     */
    public Record(int fieldId, int batchId, String batchURL, String data,
            int rowNum, int colNum) {
        this.recordId = -1;
        this.fieldId  = fieldId;
        this.batchId  = batchId;
        this.batchURL = batchURL;
        this.data     = data;
        this.RowNum   = rowNum;
        this.ColNum   = colNum;
    }

    /**
     * Instantiates a new Record.
     *
     * @param id
     * @param fieldId
     * @param batchId
     * @param batchURL
     * @param data
     * @param rowNum
     * @param colNum
     */
    public Record(int id, int fieldId, int batchId, String batchURL, String data,
            int rowNum, int colNum) {
        this.recordId = id;
        this.fieldId  = fieldId;
        this.batchId  = batchId;
        this.batchURL = batchURL;
        this.data     = data;
        this.RowNum   = rowNum;
        this.ColNum   = colNum;
    }
    //@formatter:on

    public int getRecordId() {
        return this.recordId;
    }

    public void setRecordId(int id) {
        this.recordId = id;
    }

    public int getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getBatchId() {
        return this.batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
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

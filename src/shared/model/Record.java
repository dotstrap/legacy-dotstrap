/**
 * Record.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 23, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Record.
 */
public class Record {
  public static class Value {

    private String data;

    private int valueId;

    /**
     * Default constructor
     */
    public Value() {
      valueId = -1;
      data = null;
    }

    /**
     * Initializes the value model with the specified data
     *
     * @param value The literal value or "cell" contents in db
     */
    public Value(String value) {
      this(-1, value);
    }

    /**
     * Initializes the value model with the specified data
     *
     * @param valueId The field definition this value is associated with
     * @param value The literal value or "cell" contents in db
     */
    public Value(int id, String value) {
      valueId = id;
      data = value;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    public int getValueId() {
      return valueId;
    }

    public void setValueId(int valueId) {
      this.valueId = valueId;
    }

  }

  private int recordId;
  private int fieldId;
  private int batchId;
  private String batchURL;
  private final Value data = new Value();
  private int RowNum;
  private int ColNum;

  //@formatter:off
  /**
   * Instantiates a new Record.
   */
  public Record() {
    recordId = -1;
    fieldId  = -1;
    batchId  = -1;
    batchURL = "batchURL";
    data.setData("data");
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
    recordId = -1;
    this.fieldId  = fieldId;
    this.batchId  = batchId;
    this.batchURL = batchURL;
    this.data.setData(data);
    RowNum   = rowNum;
    ColNum   = colNum;
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
    recordId = id;
    this.fieldId  = fieldId;
    this.batchId  = batchId;
    this.batchURL = batchURL;
    this.data.setData(data);
    RowNum   = rowNum;
    ColNum   = colNum;
  }
  //@formatter:on

  public int getRecordId() {
    return recordId;
  }

  public void setRecordId(int id) {
    recordId = id;
  }

  public int getFieldId() {
    return fieldId;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  public String getBatchURL() {
    return batchURL;
  }

  public void setBatchURL(String batchURL) {
    this.batchURL = batchURL;
  }

  public String getData() {
    return data.getData();
  }

  public void setData(String data) {
    this.data.setData(data);
  }

  public int getRowNum() {
    return RowNum;
  }

  public void setRowNum(int rowNum) {
    RowNum = rowNum;
  }

  public int getColNum() {
    return ColNum;
  }

  public void setColNum(int colNum) {
    ColNum = colNum;
  }
}

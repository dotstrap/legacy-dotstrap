/**
 * Record.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Record.
 */
public class Record {

  /**
   * The Class Value.
   */
  public static class Value {
    private int    valueId;
    private String data;

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
     * Initializes the value model with the specified data.
     *
     * @param id the id
     * @param value The literal value or "cell" contents in db
     */
    public Value(int id, String value) {
      valueId = id;
      data = value;
    }

    public int getValueId() {
      return valueId;
    }

    public void setValueId(int valueId) {
      this.valueId = valueId;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Value other = (Value) obj;
      if (data == null) {
        if (other.data != null) {
          return false;
        }
      } else if (!data.equals(other.data)) {
        return false;
      }
      if (valueId != other.valueId) {
        return false;
      }
      return true;
    }
  }

  private int    recordId;
  private int    fieldId;
  private int    batchId;
  private String batchURL;
  private Value  data = new Value();
  private int    RowNum;
  private int    ColNum;

  /**
   * Instantiates a new Record.
   *
   */
  public Record() {
    recordId = -1;
    fieldId = -1;
    batchId = -1;
    batchURL = "";
    data.setData(null);
    RowNum = -1;
    ColNum = -1;
  }

  /**
   * Instantiates a new record.
   *
   * @param recId the rec id
   * @param fieldId the field id
   * @param batchId the batch id
   * @param batchURL the batch url
   * @param data the data
   */
  public Record(int recId, int fieldId, int batchId, String batchURL, String data) {
    recordId = recId;
    this.fieldId = fieldId;
    this.batchId = batchId;
    this.batchURL = batchURL;
    this.data.setData(data);
    RowNum = -1;
    ColNum = -1;
  }

  /**
   * Instantiates a new Record.
   *
   * @param fieldId the field id
   * @param batchId the batch id
   * @param batchURL the batch url
   * @param data the data
   * @param rowNum the row num
   * @param colNum the col num
   */
  public Record(int fieldId, int batchId, String batchURL, String data, int rowNum, int colNum) {
    recordId = -1;
    this.fieldId = fieldId;
    this.batchId = batchId;
    this.batchURL = batchURL;
    this.data.setData(data);
    RowNum = rowNum;
    ColNum = colNum;
  }

  /**
   * Instantiates a new record.
   *
   * @param recId the rec id
   * @param fieldId the field id
   * @param batchId the batch id
   * @param batchURL the batch url
   * @param data the data
   * @param rowNum the row num
   * @param colNum the col num
   */
  public Record(int recId, int fieldId, int batchId, String batchURL, String data, int rowNum,
      int colNum) {
    recordId = recId;
    this.fieldId = fieldId;
    this.batchId = batchId;
    this.batchURL = batchURL;
    this.data.setData(data);
    RowNum = rowNum;
    ColNum = colNum;
  }

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

  /**
   * Equals.
   *
   * @param o the o
   * @param shouldCompareIds the should compare ids
   * @return true, if successful
   */
  public boolean equals(Object o, boolean shouldCompareIds) {
    if (o == null) {
      return false;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    if (o == this) {
      return true;
    }

    Record other = (Record) o;

    // if (shouldCompareIds) {
    // if (recordId != other.getRecordId()) {
    // return false;
    // }
    // }

    return (// @formatter:off
         (batchId == other.getBatchId())
        && batchURL.equals(other.getBatchURL())
        && this.getData().equals(other.getData())
        && (RowNum == other.getRowNum())
        && (ColNum == other.getColNum())); // @formatter:on
  }

}

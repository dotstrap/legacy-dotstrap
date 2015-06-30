/**
 * Record.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Record.
 */
public class Record {
  private int recordId;
  private int fieldId;
  private int batchId;
  private String batchURL;
  private String data;
  private int rowNum;
  private int colNum;

  /**
   * Instantiates a new record.
   */
  public Record() {
    recordId = -1;
    fieldId = -1;
    batchId = -1;
    batchURL = "";
    data = "";
    rowNum = -1;
    colNum = -1;
  }

  /**
   * Instantiates a new record.
   *
   * @param fieldId the field id
   * @param batchId the batch id
   * @param batchURL the batch url
   * @param data the data
   * @param rowNum the row num
   * @param colNum the col num
   */
  public Record(int fieldId, int batchId, String batchURL, String data,
      int rowNum, int colNum) {
    recordId = -1;
    this.fieldId = fieldId;
    this.batchId = batchId;
    this.batchURL = batchURL;
    this.data = data;
    this.rowNum = rowNum;
    this.colNum = colNum;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    if (o == this) {
      return true;
    }

    Record other = (Record) o;// @formatter:off
    return ((batchId == other.getBatchId())
        && (batchURL.equals(other.getBatchURL()))
        && (data.equals(other.getData()))
        && (rowNum == other.getRowNum())
        && (colNum == other.getColNum())); // @formatter:on
  }

  public int getBatchId() {
    return batchId;
  }

  public String getBatchURL() {
    return batchURL;
  }

  public int getColNum() {
    return colNum;
  }

  public String getData() {
    return data;
  }

  public int getFieldId() {
    return fieldId;
  }

  public int getRecordId() {
    return recordId;
  }

  public int getRowNum() {
    return rowNum;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  public void setBatchURL(String batchURL) {
    this.batchURL = batchURL;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public void setRecordId(int id) {
    recordId = id;
  }

  public void setRowNum(int rowNum) {
    this.rowNum = rowNum;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Record [recordId=");
    sb.append(recordId);
    sb.append(", fieldId=");
    sb.append(fieldId);
    sb.append(", batchId=");
    sb.append(batchId);
    sb.append(", ");
    if (batchURL != null) {
      sb.append("batchURL=");
      sb.append(batchURL);
      sb.append(", ");
    }
    if (data != null) {
      sb.append("data=");
      sb.append(data);
      sb.append(", ");
    }
    sb.append("rowNum=");
    sb.append(rowNum);
    sb.append(", colNum=");
    sb.append(colNum);
    sb.append("]");
    return sb.toString();
  }
}

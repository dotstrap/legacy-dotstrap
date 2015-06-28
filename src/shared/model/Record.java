package shared.model;

public class Record {
  private int recordId;
  private int fieldId;
  private int batchId;
  private String batchURL;
  private String data;
  private int rowNum;
  private int colNum;

  public Record() {
    recordId = -1;// @formatter:off
    fieldId  = -1;
    batchId  = -1;
    batchURL = "";
    data     = "";
    rowNum   = -1;
    colNum   = -1;
  }
  
  public Record(int fieldId, int batchId, String batchURL, String data, int rowNum, int colNum) {
    this.recordId = -1;
    this.fieldId  = fieldId;
    this.batchId  = batchId;
    this.batchURL = batchURL;
    this.data     = data;
    this.rowNum   = rowNum;
    this.colNum   = colNum;
  }// @formatter:on

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
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getRowNum() {
    return this.rowNum;
  }

  public void setRowNum(int rowNum) {
    this.rowNum = rowNum;
  }

  public int getColNum() {
    return this.colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

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

    Record other = (Record) o;
    return ((this.batchId == other.getBatchId()) //@formatter:off
        && (this.batchURL.equals(other.getBatchURL()))
        && (this.data.equals(other.getData()))
        && (this.rowNum == other.getRowNum())
        && (this.colNum == other.getColNum())); // @formatter:on
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Record [recordId=");
    builder.append(this.recordId);
    builder.append(", fieldId=");
    builder.append(this.fieldId);
    builder.append(", batchId=");
    builder.append(this.batchId);
    builder.append(", batchURL=");
    builder.append(this.batchURL);
    builder.append(", data=");
    builder.append(this.data);
    builder.append(", rowNum=");
    builder.append(this.rowNum);
    builder.append(", colNum=");
    builder.append(this.colNum);
    builder.append("]");
    return builder.toString();
  }

}

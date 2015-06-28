package shared.model;

public class Field implements Comparable<Field> {
  private int fieldId;
  private int projectId;
  private String title;
  private String knownData;
  private String helpURL;
  private int xCoord;
  private int width;
  private int colNum;

  public Field() {// @formatter:off
    this.fieldId = -1;
    this.projectId = -1;
    this.title = null;
    this.knownData = null;
    this.helpURL = null;
    this.xCoord = -1;
    this.width = -1;
    this.colNum = -1;
  }
  
  public Field(int projectId, String title, String knownData, String helpURL, int xCoord,
      int width, int colNum) {
    
    this.fieldId   = -1;
    this.projectId = projectId;
    this.title     = title;
    this.knownData = knownData;
    this.helpURL   = helpURL;
    this.xCoord    = xCoord;
    this.width     = width;
    this.colNum    = colNum;
  }
  
  public Field(int fieldId, int projectId, String title, String knownData, String helpURL,
      int xCoord, int width, int colNum) {

    this.fieldId   = fieldId;
    this.projectId = projectId;
    this.title     = title;
    this.knownData = knownData;
    this.helpURL   = helpURL;
    this.xCoord    = xCoord;
    this.width     = width;
    this.colNum    = colNum;
  }// @formatter:on

  public int getFieldId() {
    return fieldId;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKnownData() {
    return knownData;
  }

  public void setKnownData(String knownData) {
    this.knownData = knownData;
  }

  public String getHelpURL() {
    return helpURL;
  }

  public void setHelpURL(String helpURL) {
    this.helpURL = helpURL;
  }

  public int getXCoord() {
    return xCoord;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getColNum() {
    return colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  @Override
  public boolean equals(Object o) {
    if ((o == null) || (o.getClass() != this.getClass())) {
      return false;
    }
    if (o == this) {
      return true;
    }

    Field other = (Field) o;
    return ((projectId == other.getProjectId()) // @formatter:off
        && title.equals(other.getTitle())
        && (xCoord == other.getXCoord())
        && (width == other.getWidth())
        && helpURL.equals(other.getHelpURL())
        && knownData.equals(other.getKnownData())
        && (colNum == other.getColNum()));  // @formatter:on
  }

  @Override
  public int compareTo(Field other) {
    if (this.fieldId < other.fieldId) {
      return -1;
    } else if (this.fieldId > other.fieldId) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Field [fieldId=");
    builder.append(this.fieldId);
    builder.append(", projectId=");
    builder.append(this.projectId);
    builder.append(", title=");
    builder.append(this.title);
    builder.append(", knownData=");
    builder.append(this.knownData);
    builder.append(", helpURL=");
    builder.append(this.helpURL);
    builder.append(", xCoord=");
    builder.append(this.xCoord);
    builder.append(", width=");
    builder.append(this.width);
    builder.append(", colNum=");
    builder.append(this.colNum);
    builder.append("]");
    return builder.toString();
  }

}

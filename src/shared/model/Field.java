/**
 * Field.java
 * JRE v1.8.0_45
 *
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

/**
 * The Class Field.
 */
public class Field implements Comparable<Field> {
  private int fieldId;
  private int projectId;
  private String title;
  private String knownData;
  private String helpURL;
  private int xCoord;
  private int width;
  private int colNum;

  /**
   * Instantiates a new field.
   */
  public Field() {
    fieldId = -1;
    projectId = -1;
    title = null;
    knownData = null;
    helpURL = null;
    xCoord = -1;
    width = -1;
    colNum = -1;
  }

  /**
   * Instantiates a new field.
   *
   * @param fieldId the field id
   * @param projectId the project id
   * @param title the title
   * @param knownData the known data
   * @param helpURL the help url
   * @param xCoord the x coord
   * @param width the width
   * @param colNum the col num
   */
  public Field(int fieldId, int projectId, String title, String knownData,
      String helpURL, int xCoord, int width, int colNum) {

    this.fieldId = fieldId;
    this.projectId = projectId;
    this.title = title;
    this.knownData = knownData;
    this.helpURL = helpURL;
    this.xCoord = xCoord;
    this.width = width;
    this.colNum = colNum;
  }

  /**
   * Instantiates a new field.
   *
   * @param projectId the project id
   * @param title the title
   * @param knownData the known data
   * @param helpURL the help url
   * @param xCoord the x coord
   * @param width the width
   * @param colNum the col num
   */
  public Field(int projectId, String title, String knownData, String helpURL,
      int xCoord, int width, int colNum) {

    fieldId = -1;
    this.projectId = projectId;
    this.title = title;
    this.knownData = knownData;
    this.helpURL = helpURL;
    this.xCoord = xCoord;
    this.width = width;
    this.colNum = colNum;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Field other) {
    if (fieldId < other.fieldId) {
      return -1;
    } else if (fieldId > other.fieldId) {
      return 1;
    } else {
      return 0;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if ((o == null) || (o.getClass() != this.getClass())) {
      return false;
    }
    if (o == this) {
      return true;
    }

    Field other = (Field) o; // @formatter:off
    return ((projectId == other.getProjectId())
        && title.equals(other.getTitle())
        && (xCoord == other.getXCoord())
        && (width == other.getWidth())
        && helpURL.equals(other.getHelpURL())
        && knownData.equals(other.getKnownData())
        && (colNum == other.getColNum()));
  }// @formatter:on

  public int getColNum() {
    return colNum;
  }

  public int getFieldId() {
    return fieldId;
  }

  public String getHelpURL() {
    return helpURL;
  }

  public String getKnownData() {
    return knownData;
  }

  public int getProjectId() {
    return projectId;
  }

  public String getTitle() {
    return title;
  }

  public int getWidth() {
    return width;
  }

  public int getXCoord() {
    return xCoord;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public void setHelpURL(String helpURL) {
    this.helpURL = helpURL;
  }

  public void setKnownData(String knownData) {
    this.knownData = knownData;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Field [fieldId=");
    sb.append(fieldId);
    sb.append(", projectId=");
    sb.append(projectId);
    sb.append(", title=");
    sb.append(title);
    sb.append(", knownData=");
    sb.append(knownData);
    sb.append(", helpURL=");
    sb.append(helpURL);
    sb.append(", xCoord=");
    sb.append(xCoord);
    sb.append(", width=");
    sb.append(width);
    sb.append(", colNum=");
    sb.append(colNum);
    sb.append("]");
    return sb.toString();
  }

}

/**
 * Project.java
 * JRE v1.8.0_40
 *
 * Created by William Myers on Mar 24, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package shared.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
public class Project {
  private int    projectId;
  private String title;
  private int    recordsPerBatch;
  private int    firstYCoord;
  private int    recordHeight;

  /**
   * Instantiates a new Project.
   */
  public Project() {
    title = "";
    projectId = -1;
    recordHeight = -1;
    recordsPerBatch = -1;
    firstYCoord = -1;
  }

  /**
   * Instantiates a new Project.
   *
   * @param title the title
   * @param recordsPerBatch the records per batch
   * @param firstYCoord the first y coord
   * @param recordHeight the record height
   */
  public Project(String title, int recordsPerBatch, int firstYCoord, int recordHeight) {
    this.title = title;
    this.recordsPerBatch = recordsPerBatch;
    this.firstYCoord = firstYCoord;
    this.recordHeight = recordHeight;
  }

  /**
   * Instantiates a new Project.
   *
   * @param id the id
   * @param title the title
   * @param recordsPerBatch the records per batch
   * @param firstYCoord the first y coord
   * @param recordHeight the record height
   */
  public Project(int id, String title, int recordsPerBatch, int firstYCoord, int recordHeight) {
    projectId = id;
    this.title = title;
    this.recordsPerBatch = recordsPerBatch;
    this.firstYCoord = firstYCoord;
    this.recordHeight = recordHeight;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int id) {
    projectId = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getRecordsPerBatch() {
    return recordsPerBatch;
  }

  public void setRecordsPerBatch(int recordsPerBatch) {
    this.recordsPerBatch = recordsPerBatch;
  }

  public int getFirstYCoord() {
    return firstYCoord;
  }

  public void setFirstYCoord(int firstYCoord) {
    this.firstYCoord = firstYCoord;
  }

  public int getRecordHeight() {
    return recordHeight;
  }

  public void setRecordHeight(int recordHeight) {
    this.recordHeight = recordHeight;
  }
}
